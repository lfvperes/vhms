from rest_framework import viewsets
from rest_framework.permissions import AllowAny
from .models import Patient
from .serializers import PatientSerializer


class PatientViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = Patient.objects.all()
    serializer_class = PatientSerializer