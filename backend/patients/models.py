from django.db import models


# Create your models here.
class Species(models.TextChoices):
    CANINE = "CANINE", "Canine"
    FELINE = "FELINE", "Feline"
    AVIAN = "AVIAN", "Avian"
    RODENT = "RODENT", "Rodent"
    REPTILE = "REPTILE", "Reptile"
    AMPHIBIAN = "AMPHIBIAN", "Amphibian"
    FISH = "FISH", "Fish"
    OTHER = "OTHER", "Other"


class Patient(models.Model):
    name = models.CharField(max_length=100)
    age = models.PositiveSmallIntegerField()

    species = models.CharField(
        max_length=20,
        choices=Species.choices,
    )

    breed = models.CharField(
        max_length=100,
        blank=True,
    )

    weight = models.FloatField(
        null=True,
        blank=True,
        help_text="Weight in kilograms",
    )

    microchip = models.BooleanField(default=False)
    insurance = models.BooleanField(default=False)

    sex = models.CharField(
        max_length=1,
        choices=[
            ("M", "Male"),
            ("F", "Female"),
            ("U", "Unknown"),
        ],
        default="U",
    )

    tutor = models.ForeignKey("tutors.Tutor", on_delete=models.PROTECT)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return f"{self.name} ({self.get_species_display()})"  # type: ignore[attr-defined]
