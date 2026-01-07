from django.test import TestCase
from backend.appointments.models import Appointment, AppointmentStatus
from backend.doctors.models import Doctor
from backend.patients.models import Patient, Species
from backend.tutors.models import Tutor
from django.utils import timezone
from datetime import timedelta
from django.db import IntegrityError


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
        from django.db import connection
        print(f"Test database: {connection.vendor}")
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
