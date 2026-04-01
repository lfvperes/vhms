from rest_framework import viewsets, filters
from .models import Doctor
from .serializers import DoctorSerializer


class DoctorViewSet(viewsets.ModelViewSet):
    queryset = Doctor.objects.all()
    serializer_class = DoctorSerializer
    filter_backends = [filters.OrderingFilter]
    ordering_fields = ["full_name", "license_number", "created_at"]
    ordering = ["full_name"]