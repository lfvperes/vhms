from django.test import TestCase
from decimal import Decimal
from backend.billing.models import Invoice
from backend.tutors.models import Tutor
from backend.patients.models import Patient, Species


class InvoiceModelTest(TestCase):
    def setUp(self):
        """Create required objects for all tests."""
        self.tutor = Tutor.objects.create(
            name="Test Tutor",
            email="test.tutor@example.com",
            phone="1234567890",
        )

        self.patient = Patient.objects.create(
            name="Test Patient",
            age=5,
            species=Species.CANINE,
            sex="M",
            tutor=self.tutor,
        )

    def test_invoice_creation(self):
        """Test that an Invoice instance can be created with required fields."""
        invoice = Invoice.objects.create(
            tutor=self.tutor,
            patient=self.patient,
            status=Invoice.Status.DRAFT,
            currency="BRL",
        )

        self.assertEqual(invoice.tutor, self.tutor)
        self.assertEqual(invoice.patient, self.patient)
        self.assertEqual(invoice.status, Invoice.Status.DRAFT)
        self.assertEqual(invoice.currency, "BRL")
        self.assertIsNotNone(invoice.created_at)
        self.assertIsNotNone(invoice.updated_at)
        self.assertIsNone(invoice.issued_at)
        self.assertIsNone(invoice.due_date)

    def test_invoice_default_values(self):
        """Test that default values are set correctly."""
        invoice = Invoice.objects.create(
            tutor=self.tutor,
            patient=self.patient,
        )

        self.assertEqual(invoice.status, Invoice.Status.DRAFT)
        self.assertEqual(invoice.currency, "BRL")

    def test_invoice_str_method(self):
        """Test the __str__ method of the Invoice model."""
        invoice = Invoice.objects.create(
            tutor=self.tutor,
            patient=self.patient,
        )

        self.assertEqual(str(invoice), f"Invoice #{invoice.pk} ({invoice.status})")

    def test_invoice_without_patient(self):
        """Test that an Invoice can be created without a patient."""
        invoice = Invoice.objects.create(
            tutor=self.tutor,
            status=Invoice.Status.DRAFT,
        )

        self.assertEqual(invoice.tutor, self.tutor)
        self.assertIsNone(invoice.patient)
        self.assertEqual(invoice.status, Invoice.Status.DRAFT)

    def test_invoice_total_amount_without_items(self):
        """Test that total_amount returns 0 when there are no items."""
        invoice = Invoice.objects.create(
            tutor=self.tutor,
            patient=self.patient,
        )

        self.assertEqual(invoice.total_amount, Decimal("0"))

    def test_invoice_paid_amount_without_payments(self):
        """Test that paid_amount returns 0 when there are no payments."""
        invoice = Invoice.objects.create(
            tutor=self.tutor,
            patient=self.patient,
        )

        self.assertEqual(invoice.paid_amount, Decimal("0"))

    def test_invoice_balance_without_items_or_payments(self):
        """Test that balance returns 0 when there are no items or payments."""
        invoice = Invoice.objects.create(
            tutor=self.tutor,
            patient=self.patient,
        )

        self.assertEqual(invoice.balance, Decimal("0"))

