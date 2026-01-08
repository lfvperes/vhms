from django.db import transaction
from django.core.exceptions import ValidationError
from django.db.models import Q

from .models import Appointment, AppointmentStatus


def _has_overlapping_appointments(
    *,
    doctor,
    starts_at,
    ends_at,
    exclude_appointment_id=None,
):
    qs = Appointment.objects.filter(
        doctor=doctor,
        status=AppointmentStatus.SCHEDULED,
        starts_at__lt=ends_at,
        ends_at__gt=starts_at,
    )

    if exclude_appointment_id:
        qs = qs.exclude(id=exclude_appointment_id)

    return qs.exists()


@transaction.atomic
def create_appointment(
    *,
    doctor,
    patient,
    tutor,
    starts_at,
    ends_at,
):
    # 1. Time sanity check
    if ends_at <= starts_at:
        raise ValidationError("Appointment must end after it starts.")

    # 2. Overlap check
    if _has_overlapping_appointments(
        doctor=doctor,
        starts_at=starts_at,
        ends_at=ends_at,
    ):
        raise ValidationError(
            "Doctor already has an appointment during this time."
        )

    # 3. Create appointment
    return Appointment.objects.create(
        doctor=doctor,
        patient=patient,
        tutor=tutor,
        starts_at=starts_at,
        ends_at=ends_at,
        status=AppointmentStatus.SCHEDULED,
    )


@transaction.atomic
def reschedule_appointment(
    *,
    appointment: Appointment,
    new_starts_at,
    new_ends_at,
):
    if new_ends_at <= new_starts_at:
        raise ValidationError("Appointment must end after it starts.")

    if _has_overlapping_appointments(
        doctor=appointment.doctor,
        starts_at=new_starts_at,
        ends_at=new_ends_at,
        exclude_appointment_id=appointment.id,
    ):
        raise ValidationError(
            "Doctor already has an appointment during this time."
        )

    appointment.starts_at = new_starts_at
    appointment.ends_at = new_ends_at
    appointment.save(update_fields=["starts_at", "ends_at", "updated_at"])

    return appointment


@transaction.atomic
def cancel_appointment(*, appointment: Appointment):
    if appointment.status == AppointmentStatus.COMPLETED:
        raise ValidationError("Completed appointments cannot be cancelled.")

    if appointment.status == AppointmentStatus.CANCELLED:
        return appointment  # idempotent

    appointment.status = AppointmentStatus.CANCELLED
    appointment.save(update_fields=["status", "updated_at"])

    return appointment


@transaction.atomic
def complete_appointment(*, appointment: Appointment):
    if appointment.status == AppointmentStatus.CANCELLED:
        raise ValidationError("Cancelled appointments cannot be completed.")

    if appointment.status == AppointmentStatus.COMPLETED:
        return appointment  # idempotent

    appointment.status = AppointmentStatus.COMPLETED
    appointment.save(update_fields=["status", "updated_at"])

    return appointment
