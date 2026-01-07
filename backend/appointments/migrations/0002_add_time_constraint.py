from django.db import migrations, models

class Migration(migrations.Migration):

    dependencies = [
        ('appointments', '0001_initial'),
    ]

    operations = [
        migrations.AddConstraint(
            model_name='appointment',
            constraint=models.CheckConstraint(
                condition=models.Q(ends_at__gt=models.F('starts_at')),
                name='appointment_ends_after_start'
            ),
        ),
    ]