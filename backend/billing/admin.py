from django.contrib import admin
from .models import Invoice, InvoiceItem, Payment


class InvoiceItemInline(admin.TabularInline):
    model = InvoiceItem
    extra = 0


class PaymentInline(admin.TabularInline):
    model = Payment
    extra = 0
    readonly_fields = ("amount", "method", "paid_at")


@admin.register(Invoice)
class InvoiceAdmin(admin.ModelAdmin):
    list_display = (
        "id",
        "tutor",
        "patient",
        "status",
        "total_amount_display",
        "paid_amount_display",
        "balance_display",
        "created_at",
    )

    list_filter = ("status",)
    search_fields = ("tutor__id", "patient__id")

    readonly_fields = (
        "status",
        "issued_at",
        "created_at",
        "updated_at",
    )

    inlines = (InvoiceItemInline, PaymentInline)

    def total_amount_display(self, obj):
        return obj.total_amount

    def paid_amount_display(self, obj):
        return obj.paid_amount

    def balance_display(self, obj):
        return obj.balance

    total_amount_display.short_description = "Total"
    paid_amount_display.short_description = "Paid"
    balance_display.short_description = "Balance"


@admin.register(InvoiceItem)
class InvoiceItemAdmin(admin.ModelAdmin):
    list_display = ("id", "invoice", "description", "quantity", "unit_price")


@admin.register(Payment)
class PaymentAdmin(admin.ModelAdmin):
    list_display = ("id", "invoice", "amount", "method", "paid_at")
    readonly_fields = ("invoice", "amount", "method", "paid_at")
