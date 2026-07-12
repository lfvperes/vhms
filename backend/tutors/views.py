from rest_framework import viewsets
from rest_framework.permissions import AllowAny
from .models import Tutor
from .serializers import TutorSerializer


class TutorViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = Tutor.objects.all()
    serializer_class = TutorSerializer