# Veterinary Hospital Management System


## System Design

The system is designed around a central `VeterinaryHospital` class that manages the core entities of the hospital: `Doctor`, `Patient`, and `Appointment`. This centralized model ensures a single source of truth for the hospital's state and simplifies operations like scheduling.

Below is the UML class diagram that represents the architecture of the system.

![VHMS Class Diagram](docs/vhms-class-diagram.svg)

## Creating new apps

Always run:

```bash
docker compose run --rm web python manage.py startapp <app_name> backend/<app_name>
```

## Creating and applying migrations
### First
```bash
docker compose run --rm web python manage.py makemigrations doctors

```
or
```bash
docker compose exec web python manage.py makemigrations doctors

```
### Then

```bash
docker compose run --rm web python manage.py migrate
```
or
```bash
docker compose exec web python manage.py migrate
```


## Running this project after installation
```bash
docker compose up
```

## Running tests
### Example for a specific app
If your Docker containers are already up and running, you can execute the test command inside the web container:
```bash
docker compose exec web python manage.py test backend.doctors.tests.DoctorModelTest
```
If Docker is not running, you can start a temporary container to run the test:
```bash
docker compose run --rm web python manage.py test backend.doctors.tests.DoctorModelTest
```
### Running all tests for all apps
When Docker is Already Running (Using exec)
```bash
docker compose exec web python manage.py test
```
When Docker is Not Running (Using run)
```bash
docker compose run --rm web python manage.py test
```