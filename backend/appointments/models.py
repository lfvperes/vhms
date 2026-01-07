from django.db import models


class AppointmentStatus(models.TextChoices):
    SCHEDULED = "SCHEDULED", "scheduled"
    COMPLETED = "COMPLETED", "completed"
    CANCELLED = "CANCELLED", "cancelled"
    NO_SHOW = "NO_SHOW", "no_show"


class Appointment(models.Model):
    doctor = models.ForeignKey(
        "doctors.Doctor",
        on_delete=models.PROTECT,
    )

    patient = models.ForeignKey(
        "patients.Patient",
        on_delete=models.CASCADE,
    )

    tutor = models.ForeignKey(
        "tutors.Tutor",
        on_delete=models.CASCADE,
    )

    status = models.CharField(
        max_length=20,
        choices=AppointmentStatus.choices
    )

    starts_at = models.DateTimeField(blank=False)
    ends_at = models.DateTimeField(blank=False)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"{self.patient} - {self.doctor} - {self.starts_at}"


class Meta:
    constraints = [
        models.CheckConstraint(
            condition=models.Q(ends_at__gt=models.F("starts_at")),
            name="appointment_ends_after_start"
        )
    ]

    indexes = [
        models.Index(fields=["doctor", "starts_at"]),
        models.Index(fields=["patient", "starts_at"]),
    ]
