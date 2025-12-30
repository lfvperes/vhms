package com.vhms.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Represents a scheduled appointment with a patient.
 */
@Entity
@Table(name = "appointments") // Explicitly name the table
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id", unique = true, nullable = false) // Explicit column name for ID
    private Long id;

    // Fields for scheduling and details
    @Column(name = "appointment_date_time", nullable = false)
    private LocalDateTime appointmentDateTime;

    @Column(name = "reason", length = 500) // Added length for reason
    private String reason;

    @Column(name = "notes", columnDefinition = "TEXT") // Use TEXT for potentially long notes
    private String notes;

    @Column(name = "status", length = 50) // Status can be used to track appointment status
    private String status;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Lazy fetch, cascade updates to patient
    @JoinColumn(name = "patient_id", nullable = false) // Foreign key column in the 'appointments' table
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch for Doctor
    @JoinColumn(name = "doctor_id") // Foreign key column for Doctor
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch for Tutor
    @JoinColumn(name = "tutor_id") // Foreign key column for Tutor
    private Tutor tutor;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Assuming Appointment owns the relationship for simplicity now
    @JoinColumn(name = "billing_id", unique = true) // Unique FK if one appointment has one billing
    private Billing billing;

    // Constructors

    // Default constructor for JPA
    public Appointment() {
        this.reason = ""; // Initialize optional fields
        this.notes = "";
    }

    // Constructor for creating new appointments, establishing key relationships
    public Appointment(LocalDateTime appointmentDateTime, String reason, Patient patient, Doctor doctor) {
        if (appointmentDateTime == null || patient == null || doctor == null) {
            throw new IllegalArgumentException("Appointment date/time, patient, and doctor are required.");
        }
        this.appointmentDateTime = appointmentDateTime;
        this.reason = reason != null ? reason : "";
        this.patient = patient;
        this.doctor = doctor;
        this.notes = ""; // Default empty notes
    }

    // Constructor to include more details if needed
    public Appointment(LocalDateTime appointmentDateTime, String reason, String notes, Patient patient, Doctor doctor,
            Tutor tutor) {
        this(appointmentDateTime, reason, patient, doctor); // Call the primary constructor
        this.notes = notes != null ? notes : "";
        this.tutor = tutor;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        // Add validation if needed, e.g., ensure it's not in the past
        this.appointmentDateTime = appointmentDateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        // If the new billing is different from the old one
        if (this.billing != billing) {
            // If there was an old billing, remove the bidirectional link from it
            if (this.billing != null) {
                this.billing.setAppointment(null);
            }
            // Set the new billing
            this.billing = billing;
            // If the new billing is not null, set the bidirectional link to this appointment
            if (billing != null) {
                billing.setAppointment(this);
            }
        }
    }

}

