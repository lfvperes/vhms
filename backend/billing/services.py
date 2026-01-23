from django.db import transaction
from django.utils import timezone

from .models import Invoice, Payment
from .exceptions import DomainError


class InvoiceService:
    @staticmethod
    @transaction.atomic
    def issue(invoice: Invoice) -> None:
        if invoice.status != Invoice.Status.DRAFT:
            raise DomainError("Only draft invoices can be issued")

        if not invoice.items.exists():
            raise DomainError("Cannot issue empty invoice")

        invoice.status = Invoice.Status.ISSUED
        invoice.issued_at = timezone.now()
        invoice.save(update_fields=["status", "issued_at"])


class PaymentService:
    @staticmethod
    @transaction.atomic
    def register_payment(invoice: Invoice, amount, method) -> None:
        if invoice.status not in (
            Invoice.Status.ISSUED,
            Invoice.Status.PARTIALLY_PAID,
        ):
            raise DomainError("Invoice not payable")

        if amount <= 0:
            raise DomainError("Invalid payment amount")

        if amount > invoice.balance:
            raise DomainError("Overpayment not allowed")

        Payment.objects.create(
            invoice=invoice,
            amount=amount,
            method=method,
        )

        # Re-evaluate after payment
        if invoice.balance == 0:
            invoice.status = Invoice.Status.PAID
        else:
            invoice.status = Invoice.Status.PARTIALLY_PAID

        invoice.save(update_fields=["status"])
