from django.db import models

class Tutor(models.Model):
    name = models.CharField(max_length=255)
    email = models.EmailField(unique=True)
    phone = models.CharField(max_length=15)
    
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return f"{self.name} ({self.email}) - {self.patient_set.count()} patients"

    def get_patients_display(self):
        """Returns a list of patient names for display purposes."""
        return ", ".join([patient.name for patient in self.patient_set.all()])
