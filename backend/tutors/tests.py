from django.test import TestCase
from backend.tutors.models import Tutor


class TutorModelTest(TestCase):
    def test_tutor_creation(self):
        """
        Test that a Tutor instance can be created successfully with all fields.
        """
        tutor = Tutor.objects.create(
            name="John Doe",
            email="john.doe@example.com",
            phone="1234567890",
        )

        self.assertEqual(tutor.name, "John Doe")
        self.assertEqual(tutor.email, "john.doe@example.com")
        self.assertEqual(tutor.phone, "1234567890")
        self.assertIsNotNone(tutor.created_at)
        self.assertIsNotNone(tutor.updated_at)

    def test_tutor_str_method(self):
        """
        Test the __str__ method of the Tutor model.
        """
        tutor = Tutor.objects.create(
            name="Jane Smith",
            email="jane.smith@example.com",
            phone="9876543210",
        )
        self.assertEqual(str(tutor), "Jane Smith (jane.smith@example.com) - 0 patients")

    def test_tutor_str_method_with_patients(self):
        """
        Test the __str__ method of the Tutor model when the tutor has patients.
        """
        tutor = Tutor.objects.create(
            name="Tutor with Patients",
            email="tutor.with.patients@example.com",
            phone="5551112222",
        )

        # Add patients to the tutor
        from backend.patients.models import Patient, Species

        Patient.objects.create(
            name="Buddy",
            age=5,
            species=Species.CANINE,
            sex="M",
            tutor=tutor,
        )

        Patient.objects.create(
            name="Whiskers",
            age=3,
            species=Species.FELINE,
            sex="F",
            tutor=tutor,
        )

        # Verify the __str__ method includes the patient count
        self.assertEqual(
            str(tutor),
            "Tutor with Patients (tutor.with.patients@example.com) - 2 patients"
        )

    def test_email_uniqueness(self):
        """
        Test that the email field enforces uniqueness.
        """
        Tutor.objects.create(
            name="Alice Johnson",
            email="alice.johnson@example.com",
            phone="5551234567",
        )

        # Attempting to create another tutor with the same email should raise an error
        with self.assertRaises(Exception):
            Tutor.objects.create(
                name="Bob Brown",
                email="alice.johnson@example.com",  # Duplicate email
                phone="5559876543",
            )

    def test_optional_fields(self):
        """
        Test that optional fields (phone) can be set or omitted.
        """
        tutor_with_phone = Tutor.objects.create(
            name="Charlie Davis",
            email="charlie.davis@example.com",
            phone="1112223333",
        )
        self.assertEqual(tutor_with_phone.phone, "1112223333")

        # Phone is not optional, so this test is not applicable
        # However, if phone were optional, we would test it here

    def test_get_patients_display(self):
        """
        Test the get_patients_display method of the Tutor model.
        """
        tutor = Tutor.objects.create(
            name="Tutor with Patients",
            email="tutor.with.patients@example.com",
            phone="5551112222",
        )

        # Initially, the tutor has no patients
        self.assertEqual(tutor.get_patients_display(), "")

        # Add patients to the tutor (assuming the Patient model is available)
        from backend.patients.models import Patient, Species

        patient1 = Patient.objects.create(
            name="Buddy",
            age=5,
            species=Species.CANINE,
            sex="M",
            tutor=tutor,
        )

        patient2 = Patient.objects.create(
            name="Whiskers",
            age=3,
            species=Species.FELINE,
            sex="F",
            tutor=tutor,
        )

        # Verify the get_patients_display method
        self.assertEqual(tutor.get_patients_display(), "Buddy, Whiskers")
