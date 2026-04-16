from rest_framework import serializers
from .models import Invoice, Payment


class InvoiceSerializer(serializers.ModelSerializer):
    class Meta:
        model = Invoice
        fields = "__all__"


class PaymentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Payment
        fields = "__all__"

        # Invoice is provided by nested route, not client input
        read_only_fields = (
            "id",
            "invoice",
            "paid_at",
            "created_at",
        )
