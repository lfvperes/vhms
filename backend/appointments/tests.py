from django.test import TestCase
from backend.appointments.models import Appointment, AppointmentStatus
from backend.appointments.services import (
    create_appointment,
    reschedule_appointment,
    cancel_appointment,
    complete_appointment,
)
from backend.doctors.models import Doctor
from backend.patients.models import Patient, Species
from backend.tutors.models import Tutor
from django.utils import timezone
from datetime import timedelta
from django.db import IntegrityError
from django.core.exceptions import ValidationError


class AppointmentModelTest(TestCase):
    def setUp(self):
        """Create required objects for all tests."""
        self.tutor = Tutor.objects.create(
            name="Test Tutor",
            email="test.tutor@example.com",
            phone="1234567890",
        )

        self.doctor = Doctor.objects.create(
            full_name="Dr. Test Doctor",
            license_number="LIC12345",
            specialty="General Practice",
        )

        self.patient = Patient.objects.create(
            name="Test Patient",
            age=5,
            species=Species.CANINE,
            sex="M",
            tutor=self.tutor,
        )

    def test_appointment_creation(self):
        """
        Test that an Appointment instance can be created successfully with all fields.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        appointment = Appointment.objects.create(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            status=AppointmentStatus.SCHEDULED,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        self.assertEqual(appointment.doctor, self.doctor)
        self.assertEqual(appointment.patient, self.patient)
        self.assertEqual(appointment.tutor, self.tutor)
        self.assertEqual(appointment.status, AppointmentStatus.SCHEDULED)
        self.assertEqual(appointment.starts_at, starts_at)
        self.assertEqual(appointment.ends_at, ends_at)
        self.assertIsNotNone(appointment.created_at)
        self.assertIsNotNone(appointment.updated_at)

    def test_appointment_str_method(self):
        """
        Test the __str__ method of the Appointment model.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        appointment = Appointment.objects.create(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            status=AppointmentStatus.SCHEDULED,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        self.assertEqual(
            str(appointment),
            f"{self.patient} - {self.doctor} - {starts_at}"
        )

    def test_appointment_status_choices(self):
        """
        Test that all defined appointment status choices are valid and can be assigned.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        for status_value, status_display in AppointmentStatus.choices:
            appointment = Appointment.objects.create(
                doctor=self.doctor,
                patient=self.patient,
                tutor=self.tutor,
                status=status_value,
                starts_at=starts_at,
                ends_at=ends_at,
            )
            self.assertEqual(appointment.status, status_value)

    def test_appointment_ends_after_start_constraint(self):
        """
        Test that the ends_at field must be after the starts_at field.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        # Valid appointment (ends_at > starts_at)
        appointment = Appointment.objects.create(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            status=AppointmentStatus.SCHEDULED,
            starts_at=starts_at,
            ends_at=ends_at,
        )
        self.assertIsNotNone(appointment)

        # Invalid appointment (ends_at < starts_at)
        invalid_ends_at = starts_at - timedelta(hours=1)
        with self.assertRaises(IntegrityError):
            Appointment.objects.create(
                doctor=self.doctor,
                patient=self.patient,
                tutor=self.tutor,
                status=AppointmentStatus.SCHEDULED,
                starts_at=starts_at,
                ends_at=invalid_ends_at,
            )

    def test_appointment_indexes(self):
        """
        Test that the indexes for doctor-starts_at and patient-starts_at are created.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        appointment = Appointment.objects.create(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            status=AppointmentStatus.SCHEDULED,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        # Verify that the appointment can be retrieved using the indexed fields
        appointments_by_doctor = Appointment.objects.filter(
            doctor=self.doctor, starts_at=starts_at
        )
        self.assertEqual(appointments_by_doctor.count(), 1)

        appointments_by_patient = Appointment.objects.filter(
            patient=self.patient, starts_at=starts_at
        )
        self.assertEqual(appointments_by_patient.count(), 1)


class AppointmentServiceTest(TestCase):
    def setUp(self):
        """Create required objects for all tests."""
        self.tutor = Tutor.objects.create(
            name="Test Tutor",
            email="test.tutor@example.com",
            phone="1234567890",
        )

        self.doctor = Doctor.objects.create(
            full_name="Dr. Test Doctor",
            license_number="LIC12345",
            specialty="General Practice",
        )

        self.patient = Patient.objects.create(
            name="Test Patient",
            age=5,
            species=Species.CANINE,
            sex="M",
            tutor=self.tutor,
        )

    def test_create_appointment_success(self):
        """
        Test that an appointment can be created successfully using the service.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        appointment = create_appointment(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        self.assertEqual(appointment.doctor, self.doctor)
        self.assertEqual(appointment.patient, self.patient)
        self.assertEqual(appointment.tutor, self.tutor)
        self.assertEqual(appointment.status, AppointmentStatus.SCHEDULED)
        self.assertEqual(appointment.starts_at, starts_at)
        self.assertEqual(appointment.ends_at, ends_at)

    def test_create_appointment_invalid_time(self):
        """
        Test that creating an appointment with invalid time raises ValidationError.
        """
        starts_at = timezone.now() + timedelta(hours=2)
        ends_at = starts_at - timedelta(hours=1)

        with self.assertRaises(ValidationError):
            create_appointment(
                doctor=self.doctor,
                patient=self.patient,
                tutor=self.tutor,
                starts_at=starts_at,
                ends_at=ends_at,
            )

    def test_create_appointment_overlap(self):
        """
        Test that creating an overlapping appointment raises ValidationError.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        # Create the first appointment
        create_appointment(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        # Attempt to create an overlapping appointment
        with self.assertRaises(ValidationError):
            create_appointment(
                doctor=self.doctor,
                patient=self.patient,
                tutor=self.tutor,
                starts_at=starts_at + timedelta(minutes=30),
                ends_at=ends_at + timedelta(minutes=30),
            )

    def test_reschedule_appointment_success(self):
        """
        Test that an appointment can be rescheduled successfully.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        appointment = create_appointment(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        new_starts_at = starts_at + timedelta(days=1)
        new_ends_at = new_starts_at + timedelta(hours=2)

        rescheduled_appointment = reschedule_appointment(
            appointment=appointment,
            new_starts_at=new_starts_at,
            new_ends_at=new_ends_at,
        )

        self.assertEqual(rescheduled_appointment.starts_at, new_starts_at)
        self.assertEqual(rescheduled_appointment.ends_at, new_ends_at)

    def test_reschedule_appointment_invalid_time(self):
        """
        Test that rescheduling an appointment with invalid time raises ValidationError.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        appointment = create_appointment(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        new_starts_at = timezone.now() + timedelta(hours=3)
        new_ends_at = new_starts_at - timedelta(hours=1)

        with self.assertRaises(ValidationError):
            reschedule_appointment(
                appointment=appointment,
                new_starts_at=new_starts_at,
                new_ends_at=new_ends_at,
            )

    def test_reschedule_appointment_overlap(self):
        """
        Test that rescheduling an appointment to an overlapping time raises ValidationError.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        appointment1 = create_appointment(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        appointment2 = create_appointment(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            starts_at=starts_at + timedelta(days=1),
            ends_at=ends_at + timedelta(days=1),
        )

        # Attempt to reschedule appointment2 to overlap with appointment1
        with self.assertRaises(ValidationError):
            reschedule_appointment(
                appointment=appointment2,
                new_starts_at=starts_at + timedelta(minutes=30),
                new_ends_at=ends_at + timedelta(minutes=30),
            )

    def test_cancel_appointment_success(self):
        """
        Test that an appointment can be cancelled successfully.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        appointment = create_appointment(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        cancelled_appointment = cancel_appointment(appointment=appointment)

        self.assertEqual(cancelled_appointment.status, AppointmentStatus.CANCELLED)

    def test_cancel_appointment_already_cancelled(self):
        """
        Test that cancelling an already cancelled appointment is idempotent.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        appointment = create_appointment(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        # Cancel the appointment
        cancelled_appointment = cancel_appointment(appointment=appointment)
        self.assertEqual(cancelled_appointment.status, AppointmentStatus.CANCELLED)

        # Attempt to cancel again (should be idempotent)
        cancelled_appointment_again = cancel_appointment(appointment=appointment)
        self.assertEqual(cancelled_appointment_again.status, AppointmentStatus.CANCELLED)

    def test_cancel_appointment_completed(self):
        """
        Test that cancelling a completed appointment raises ValidationError.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        appointment = create_appointment(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        # Complete the appointment
        complete_appointment(appointment=appointment)

        # Attempt to cancel the completed appointment
        with self.assertRaises(ValidationError):
            cancel_appointment(appointment=appointment)

    def test_complete_appointment_success(self):
        """
        Test that an appointment can be completed successfully.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        appointment = create_appointment(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        completed_appointment = complete_appointment(appointment=appointment)

        self.assertEqual(completed_appointment.status, AppointmentStatus.COMPLETED)

    def test_complete_appointment_already_completed(self):
        """
        Test that completing an already completed appointment is idempotent.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        appointment = create_appointment(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        # Complete the appointment
        completed_appointment = complete_appointment(appointment=appointment)
        self.assertEqual(completed_appointment.status, AppointmentStatus.COMPLETED)

        # Attempt to complete again (should be idempotent)
        completed_appointment_again = complete_appointment(appointment=appointment)
        self.assertEqual(completed_appointment_again.status, AppointmentStatus.COMPLETED)

    def test_complete_appointment_cancelled(self):
        """
        Test that completing a cancelled appointment raises ValidationError.
        """
        starts_at = timezone.now() + timedelta(hours=1)
        ends_at = starts_at + timedelta(hours=2)

        appointment = create_appointment(
            doctor=self.doctor,
            patient=self.patient,
            tutor=self.tutor,
            starts_at=starts_at,
            ends_at=ends_at,
        )

        # Cancel the appointment
        cancel_appointment(appointment=appointment)

        # Attempt to complete the cancelled appointment
        with self.assertRaises(ValidationError):
            complete_appointment(appointment=appointment)
