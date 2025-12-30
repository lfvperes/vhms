package com.vhms.model;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.vhms.model.species.Canine; // Keep this as it's from a different subpackage

public class AppointmentTest {

    @Test
    public void testCreateAppointment() {
        // Arrange
        LocalDateTime appointmentDateTime = LocalDateTime.now();
        String reason = "Test reason";
        Canine patient = new Canine((byte)1, "Breed", false, false, "Buddy", true, null, 10.0f); // Initialize Canine
        Doctor doctor = new Doctor(); // Assuming Doctor has a default constructor

        // Act
        Appointment appointment = new Appointment(appointmentDateTime, reason, patient, doctor);

        // Assert
        assertNotNull(appointment);
        assertEquals(appointmentDateTime, appointment.getAppointmentDateTime());
        assertEquals(reason, appointment.getReason());
        assertEquals(patient, appointment.getPatient());
        assertEquals(doctor, appointment.getDoctor());
    }

    @Test
    public void testCreateAppointmentWithNotes() {
        // Arrange
        LocalDateTime appointmentDateTime = LocalDateTime.now();
        String reason = "Test reason";
        String notes = "Test notes";
        Canine patient = new Canine((byte)2, "Another Breed", true, true, "Max", false, null, 20.0f); // Initialize Canine
        Doctor doctor = new Doctor(); // Assuming Doctor has a default constructor
        Tutor tutor = new Tutor(); // Assuming Tutor has a default constructor

        // Act
        Appointment appointment = new Appointment(appointmentDateTime, reason, notes, patient, doctor, tutor);

        // Assert
        assertNotNull(appointment);
        assertEquals(appointmentDateTime, appointment.getAppointmentDateTime());
        assertEquals(reason, appointment.getReason());
        assertEquals(notes, appointment.getNotes());
        assertEquals(patient, appointment.getPatient());
        assertEquals(doctor, appointment.getDoctor());
        assertEquals(tutor, appointment.getTutor());
    }

    @Test
    public void testSetAppointmentDateTime() {
        // Arrange
        Appointment appointment = new Appointment();
        LocalDateTime newAppointmentDateTime = LocalDateTime.now();

        // Act
        appointment.setAppointmentDateTime(newAppointmentDateTime);

        // Assert
        assertEquals(newAppointmentDateTime, appointment.getAppointmentDateTime());
    }

    @Test
    public void testSetReason() {
        // Arrange
        Appointment appointment = new Appointment();
        String newReason = "New reason";

        // Act
        appointment.setReason(newReason);

        // Assert
        assertEquals(newReason, appointment.getReason());
    }

    @Test
    public void testSetPatient() {
        // Arrange
        Appointment appointment = new Appointment();
        Canine newPatient = new Canine((byte)3, "Test Breed", false, true, "Rex", true, null, 15.0f); // Initialize Canine

        // Act
        appointment.setPatient(newPatient);

        // Assert
        assertEquals(newPatient, appointment.getPatient());
    }

    @Test
    public void testSetDoctor() {
        // Arrange
        Appointment appointment = new Appointment();
        Doctor newDoctor = new Doctor(); // Assuming Doctor has a default constructor

        // Act
        appointment.setDoctor(newDoctor);

        // Assert
        assertEquals(newDoctor, appointment.getDoctor());
    }

    @Test
    public void testSetTutor() {
        // Arrange
        Appointment appointment = new Appointment();
        Tutor newTutor = new Tutor(); // Assuming Tutor has a default constructor

        // Act
        appointment.setTutor(newTutor);

        // Assert
        assertEquals(newTutor, appointment.getTutor());
    }
}
