from rest_framework import viewsets, filters
from rest_framework.permissions import AllowAny
from django_filters.rest_framework import DjangoFilterBackend
from .models import Doctor
from .serializers import DoctorSerializer


class DoctorViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = Doctor.objects.all()
    serializer_class = DoctorSerializer

    filter_backends = [
        DjangoFilterBackend,
        filters.OrderingFilter,
        filters.SearchFilter,
    ]

    filterset_fields = ["is_active", "specialty"]
    ordering_fields = ["full_name", "license_number", "created_at"]
    ordering = ["full_name"]

    search_fields = [
        "full_name",
        "license_number",
        "specialty",
    ]