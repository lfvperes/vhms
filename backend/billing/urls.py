from rest_framework.routers import DefaultRouter
from .views import InvoiceViewSet

router = DefaultRouter()

# Register invoice endpoints:
# /api/invoices/
# /api/invoices/{id}/
#
# Also enables nested custom actions like:
# /api/invoices/{id}/payments/
router.register(r"invoices", InvoiceViewSet)

urlpatterns = router.urls
