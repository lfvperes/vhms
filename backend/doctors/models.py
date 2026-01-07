from django.db import models


class Doctor(models.Model):
    full_name = models.CharField(max_length=150)
    license_number = models.CharField(max_length=50, unique=True)
    specialty = models.CharField(max_length=100, blank=True)
    is_active = models.BooleanField(default=True)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        ordering = ["full_name", "license_number"]

    def __str__(self):
        return f"Dr. {self.full_name}, {self.specialty} - {self.license_number}"
