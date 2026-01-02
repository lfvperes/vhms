# Veterinary Hospital Management System


## System Design

The system is designed around a central `VeterinaryHospital` class that manages the core entities of the hospital: `Doctor`, `Patient`, and `Appointment`. This centralized model ensures a single source of truth for the hospital's state and simplifies operations like scheduling.

Below is the UML class diagram that represents the architecture of the system.

![VHMS Class Diagram](docs/vhms-class-diagram.svg)

## Creating new apps

Always run:

docker compose run --rm web python manage.py startapp <app_name> backend/<app_name>
