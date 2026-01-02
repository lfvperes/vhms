from django.test import TestCase
from backend.patients.models import Patient, Species
from backend.tutors.models import Tutor

class PatientModelTest(TestCase):
    def setUp(self):
        """Create a tutor for use in all tests."""
        self.tutor = Tutor.objects.create(
            name="Test Tutor",
            email="test.tutor@example.com",
            phone="1234567890",
        )

    def test_patient_creation(self):
        """
        Test that a Patient instance can be created successfully with all fields,
        including optional ones being set or omitted as per model definition.
        """
        patient = Patient.objects.create(
            name="Buddy",
            age=5,
            species=Species.CANINE,
            sex="M",
            weight=25.5,
            breed="Golden Retriever",
            microchip=True,
            insurance=False,
            tutor=self.tutor,  # Required field
        )

        self.assertEqual(patient.name, "Buddy")
        self.assertEqual(patient.age, 5)
        self.assertEqual(patient.species, Species.CANINE)
        self.assertEqual(patient.get_species_display(), "Canine")
        self.assertEqual(patient.sex, "M")
        self.assertEqual(patient.weight, 25.5)
        self.assertEqual(patient.breed, "Golden Retriever")
        self.assertTrue(patient.microchip)
        self.assertFalse(patient.insurance)
        self.assertEqual(patient.tutor, self.tutor)  # Verify tutor is assigned
        self.assertIsNotNone(patient.created_at)
        self.assertIsNotNone(patient.updated_at)

    def test_patient_str_method(self):
        """
        Test the __str__ method of the Patient model.
        """
        patient = Patient.objects.create(
            name="Whiskers",
            age=3,
            species=Species.FELINE,
            sex="F",
            tutor=self.tutor,
        )
        self.assertEqual(str(patient), "Whiskers (Feline)")

    def test_species_choices(self):
        """
        Test that all defined species choices are valid and can be assigned.
        """
        for species_value, species_display in Species.choices:
            patient = Patient.objects.create(
                name=f"Test Patient {species_value}",
                age=1,
                species=species_value,
                sex="U",  # Using 'U' as default for sex
                tutor=self.tutor,
            )
            self.assertEqual(patient.species, species_value)
            self.assertEqual(patient.get_species_display(), species_display)

    def test_optional_fields_blank_null(self):
        """
        Test that optional fields (breed, weight) can be left blank or null.
        """
        patient = Patient.objects.create(
            name="Rocky",
            age=7,
            species=Species.RODENT,
            sex="M",
            tutor=self.tutor,
            # breed and weight are omitted, should default to blank/None
        )
        self.assertEqual(
            patient.breed, ""
        )  # breed is blank=True, defaults to empty string
        self.assertIsNone(patient.weight)  # weight is null=True, defaults to None

        # Also test explicitly setting blank/null values
        patient_with_blanks = Patient.objects.create(
            name="Empty",
            age=2,
            species=Species.OTHER,
            sex="U",
            breed="",
            weight=None,
            tutor=self.tutor,
        )
        self.assertEqual(patient_with_blanks.breed, "")
        self.assertIsNone(patient_with_blanks.weight)

    def test_sex_choices_and_default(self):
        """
        Test that sex choices are respected and the default value ('U') is applied.
        """
        patient_male = Patient.objects.create(
            name="Max", age=4, species=Species.CANINE, sex="M", tutor=self.tutor
        )
        self.assertEqual(patient_male.sex, "M")

        patient_female = Patient.objects.create(
            name="Lucy", age=2, species=Species.FELINE, sex="F", tutor=self.tutor
        )
        self.assertEqual(patient_female.sex, "F")

        # Patient created without specifying sex should use the default "U"
        patient_unknown = Patient.objects.create(
            name="Zoe", age=1, species=Species.AVIAN, tutor=self.tutor
        )
        self.assertEqual(patient_unknown.sex, "U")

    def test_age_field(self):
        """
        Test that the age field accepts a positive small integer.
        """
        patient_age_one = Patient.objects.create(
            name="Youngling", age=1, species=Species.FISH, tutor=self.tutor
        )
        self.assertEqual(patient_age_one.age, 1)

        patient_age_max = Patient.objects.create(
            name="Oldie", age=32767, species=Species.OTHER, tutor=self.tutor
        )  # Max for SmallIntegerField
        self.assertEqual(patient_age_max.age, 32767)

    def test_default_boolean_fields(self):
        """
        Test default values for microchip and insurance fields.
        """
        # When not specified, both should be False
        patient_defaults = Patient.objects.create(
            name="DefaultPet", age=1, species=Species.OTHER, sex="U", tutor=self.tutor
        )
        self.assertFalse(patient_defaults.microchip)
        self.assertFalse(patient_defaults.insurance)

        # When explicitly set
        patient_modified = Patient.objects.create(
            name="ModPet",
            age=1,
            species=Species.OTHER,
            sex="U",
            microchip=True,
            insurance=True,
            tutor=self.tutor,
        )
        self.assertTrue(patient_modified.microchip)
        self.assertTrue(patient_modified.insurance)
