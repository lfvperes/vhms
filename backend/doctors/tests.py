from django.test import TestCase
from backend.doctors.models import Doctor


class DoctorModelTest(TestCase):
    def test_doctor_creation(self):
        """
        Test that a Doctor instance can be created successfully with all fields.
        """
        doctor = Doctor.objects.create(
            full_name="John Doe",
            license_number="LIC12345",
            specialty="Cardiology",
            is_active=True,
        )

        self.assertEqual(doctor.full_name, "John Doe")
        self.assertEqual(doctor.license_number, "LIC12345")
        self.assertEqual(doctor.specialty, "Cardiology")
        self.assertTrue(doctor.is_active)
        self.assertIsNotNone(doctor.created_at)
        self.assertIsNotNone(doctor.updated_at)

    def test_doctor_str_method(self):
        """
        Test the __str__ method of the Doctor model.
        """
        doctor = Doctor.objects.create(
            full_name="Jane Smith",
            license_number="LIC67890",
            specialty="Pediatrics",
        )
        self.assertEqual(str(doctor), "Dr. Jane Smith, Pediatrics - LIC67890")

    def test_license_number_uniqueness(self):
        """
        Test that the license_number field enforces uniqueness.
        """
        Doctor.objects.create(
            full_name="Alice Johnson",
            license_number="LIC11111",
            specialty="Neurology",
        )

        # Attempting to create another doctor with the same license_number should raise an error
        with self.assertRaises(Exception):
            Doctor.objects.create(
                full_name="Bob Brown",
                license_number="LIC11111",  # Duplicate license_number
                specialty="Dermatology",
            )

    def test_optional_specialty_field(self):
        """
        Test that the specialty field can be left blank.
        """
        doctor_without_specialty = Doctor.objects.create(
            full_name="Charlie Davis",
            license_number="LIC22222",
            # specialty is omitted, should default to blank
        )
        self.assertEqual(doctor_without_specialty.specialty, "")

    def test_default_is_active_field(self):
        """
        Test that the is_active field defaults to True.
        """
        doctor_default_active = Doctor.objects.create(
            full_name="David Wilson",
            license_number="LIC33333",
        )
        self.assertTrue(doctor_default_active.is_active)

    def test_ordering(self):
        """
        Test that the default ordering is by full_name and license_number.
        """
        doctor1 = Doctor.objects.create(
            full_name="Eve Adams",
            license_number="LIC55555",
        )
        doctor2 = Doctor.objects.create(
            full_name="Frank Brown",
            license_number="LIC44444",
        )

        # Retrieve all doctors and verify the order
        doctors = Doctor.objects.all()
        self.assertEqual(doctors[0], doctor1)
        self.assertEqual(doctors[1], doctor2)
