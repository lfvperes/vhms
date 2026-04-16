from rest_framework import viewsets, status
from rest_framework.decorators import action
from rest_framework.response import Response

from .models import Invoice
from .serializers import InvoiceSerializer, PaymentSerializer


class InvoiceViewSet(viewsets.ModelViewSet):
    queryset = Invoice.objects.all()
    serializer_class = InvoiceSerializer

    # Custom nested endpoint:
    # /api/invoices/{id}/payments/
    #
    # Allows listing payments for a specific invoice
    # and creating new payments tied to that invoice.
    @action(detail=True, methods=["get", "post"])
    def payments(self, request, pk=None):
        # Get the parent invoice from the URL's {id}
        invoice = self.get_object()

        # GET /api/invoices/{id}/payments/
        # Return all payments belonging to this invoice
        if request.method == "GET":
            serializer = PaymentSerializer(
                invoice.payments.all(),
                many=True,
            )
            return Response(serializer.data)

        # POST /api/invoices/{id}/payments/
        # Create a payment for this invoice
        serializer = PaymentSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)

        # Inject invoice from URL instead of client payload
        serializer.save(invoice=invoice)

        return Response(
            serializer.data,
            status=status.HTTP_201_CREATED,
        )
