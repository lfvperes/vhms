## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## System Design

The system is designed around a central `VeterinaryHospital` class that manages the core entities of the hospital: `Doctor`, `Patient`, and `Appointment`. This centralized model ensures a single source of truth for the hospital's state and simplifies operations like scheduling.

Below is the UML class diagram that represents the architecture of the system.

![VHMS Class Diagram](docs/vhms-class-diagram.svg)
