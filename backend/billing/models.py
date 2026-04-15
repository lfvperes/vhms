from decimal import Decimal
from django.db import models
from django.utils import timezone
from django.db.models import Sum


class Invoice(models.Model):
    class Status(models.TextChoices):
        DRAFT = "draft"
        ISSUED = "issued"
        PAID = "paid"
        PARTIALLY_PAID = "partially_paid"
        CANCELLED = "cancelled"

    tutor = models.ForeignKey(
        'tutors.Tutor',
        on_delete=models.PROTECT,
        related_name="invoices",
    )
    patient = models.ForeignKey(
        'patients.Patient',
        null=True,
        blank=True,
        on_delete=models.PROTECT,
        related_name="invoices",
    )

    status = models.CharField(
        max_length=20,
        choices=Status.choices,
        default=Status.DRAFT,
    )

    issued_at = models.DateTimeField(null=True, blank=True)
    due_date = models.DateField(null=True, blank=True)

    currency = models.CharField(max_length=3, default="BRL")

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    @property
    def total_amount(self) -> Decimal:
        return sum(
            (item.quantity * item.unit_price for item in self.items.all()),
            Decimal("0"),
        )

    @property
    def paid_amount(self) -> Decimal:
        return sum(
            (p.amount for p in self.payments.all()),
            Decimal("0"),
        )

    @property
    def balance(self) -> Decimal:
        return self.total_amount - self.paid_amount

    def __str__(self) -> str:
        return f"Invoice #{self.pk} ({self.status})"

    items: "models.Manager"
    payments: "models.Manager"

    def save(self, *args, **kwargs):
        bypass = kwargs.pop("_bypass_status_lock", False)

        if self.pk and not bypass:
            original = Invoice.objects.get(pk=self.pk)

            # Block ANY changes if not draft
            if original.status != self.Status.DRAFT:
                raise ValueError(
                    f"Cannot modify invoice in '{original.status}' status"
                )

            # Also block changing status directly
            if self.status != original.status:
                raise ValueError("Status changes must use domain methods")

        super().save(*args, **kwargs)

    def issue(self):
        if self.status != self.Status.DRAFT:
            raise ValueError("Only draft invoices can be issued")

        self.status = self.Status.ISSUED
        self.issued_at = timezone.now()

        self.save(_bypass_status_lock=True)

    def cancel(self):
        if self.status != self.Status.ISSUED:
            raise ValueError("Only issued invoices can be cancelled")

        self.status = self.Status.CANCELLED

        self.save(_bypass_status_lock=True)

    def total_paid(self):
        return self.payments.aggregate(total=Sum("amount"))["total"] or 0

    def update_payment_status(self):
        paid = self.total_paid()

        if paid == 0:
            return  # stay issued

        if paid < self.total_amount:
            self.status = self.Status.PARTIALLY_PAID

        elif paid == self.total_amount:
            self.status = self.Status.PAID

        self.save(update_fields=["status"], _bypass_status_lock=True)


class InvoiceItem(models.Model):
    invoice = models.ForeignKey(
        Invoice,
        on_delete=models.CASCADE,
        related_name="items",
    )

    description = models.CharField(max_length=255)

    quantity = models.DecimalField(
        max_digits=10,
        decimal_places=2,
        default=Decimal("1"),
    )
    unit_price = models.DecimalField(max_digits=10, decimal_places=2)

    appointment = models.ForeignKey(
        'appointments.Appointment',
        null=True,
        blank=True,
        on_delete=models.SET_NULL,
        related_name="invoice_items",
    )

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self) -> str:
        return f"{self.description} x {self.quantity}"


class Payment(models.Model):
    class Method(models.TextChoices):
        CASH = "cash"
        CREDIT = "credit"
        DEBIT = "debit"
        PIX = "pix"
        TRANSFER = "transfer"

    invoice = models.ForeignKey(
        Invoice,
        on_delete=models.PROTECT,
        related_name="payments",
    )

    amount = models.DecimalField(max_digits=10, decimal_places=2)
    method = models.CharField(max_length=20, choices=Method.choices)

    paid_at = models.DateTimeField(auto_now_add=True)
    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self) -> str:
        return f"Payment {self.amount} ({self.method})"

    def save(self, *args, **kwargs):
        if not self.invoice:
            raise ValueError("Payment must be associated with an invoice")

        if self.pk:
            raise ValueError("Payments cannot be modified after creation")

        invoice = self.invoice

        if invoice.status in [invoice.Status.DRAFT, invoice.Status.CANCELLED]:
            raise ValueError(
                f"Cannot register payment for invoice in '{invoice.status}' status"
            )

        if not self.pk:
            if self.amount > invoice.balance:
                raise ValueError("Payment exceeds invoice balance")

        super().save(*args, **kwargs)

        # after saving, update invoice state
        self.invoice.update_payment_status()

    def delete(self, *args, **kwargs):
        raise ValueError("Payments cannot be deleted")
