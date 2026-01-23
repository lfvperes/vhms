from .models import Invoice, InvoiceItem


class InvoiceService:
    @staticmethod
    def issue(invoice: Invoice):
        if invoice.status != Invoice.Status.DRAFT:
            raise DomainError("Only draft invoices can be issued")

        if not invoice.items.exists():
            raise DomainError("Cannot issue empty invoice")

        invoice.status = Invoice.Status.ISSUED
        invoice.issued_at = timezone.now()
        invoice.save()


class PaymentService:
    @staticmethod
    def register_payment(invoice: Invoice, amount, method):
        if invoice.status not in [
            Invoice.Status.ISSUED,
            Invoice.Status.PARTIALLY_PAID,
        ]:
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

        if invoice.balance == 0:
            invoice.status = Invoice.Status.PAID
        else:
            invoice.status = Invoice.Status.PARTIALLY_PAID

        invoice.save()
