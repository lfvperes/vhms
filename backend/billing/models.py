from decimal import Decimal
from django.db import models


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
