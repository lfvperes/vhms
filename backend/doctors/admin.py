from django.contrib import admin
from .models import Doctor


@admin.register(Doctor)
class DoctorAdmin(admin.ModelAdmin):
    list_display = (
        "id",
        "full_name",
        "license_number",
        "specialty",
        "is_active",
    )

    list_filter = ("is_active", "specialty")
    search_fields = ("full_name", "license_number")
