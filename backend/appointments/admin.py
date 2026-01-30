from django.contrib import admin
from .models import Appointment


@admin.register(Appointment)
class AppointmentAdmin(admin.ModelAdmin):
    list_display = (
        "id",
        "doctor",
        "patient",
        "starts_at",
        "ends_at",
        "status",
    )

    list_filter = ("status", "doctor")
    search_fields = ("patient__id", "doctor__id")
