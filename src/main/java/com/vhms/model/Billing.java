package com.vhms.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType; // Import FetchType
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne; // Import OneToOne
import jakarta.persistence.Table;

@Entity
@Table(name = "billings")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "paid")
    private boolean paid;

    // The relationship is now owned by Billing, with a unique FK to Appointment.
    // FetchType.LAZY is default, but explicitly stated for clarity.
    // CascadeType.MERGE is used here, as we usually merge an existing appointment, not create a new one for billing.
    // If a new appointment is created *with* a billing, Appointment's setter should handle it.
    @OneToOne(fetch = FetchType.LAZY, cascade = { jakarta.persistence.CascadeType.MERGE, jakarta.persistence.CascadeType.REFRESH})
    @JoinColumn(name = "appointment_id", nullable = false, unique = true) // FK in the billings table, unique constraint
    private Appointment appointment;

    // Constructors
    public Billing() {
    }

    // Constructor to create a billing associated with an appointment
    public Billing(String description, BigDecimal amount, boolean paid, Appointment appointment) {
        this.description = description;
        this.amount = amount;
        this.paid = paid;
        // Setting the appointment here ensures the bidirectional link is established upon creation
        setAppointment(appointment);
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        // If the new appointment is different from the old one
        if (this.appointment != appointment) {
            // If there was an old appointment, remove this billing from its list
            if (this.appointment != null) {
                this.appointment.setBilling(null); // Break the bidirectional link
            }
            // Set the new appointment
            this.appointment = appointment;
            // If the new appointment is not null, add this billing to its billing
            if (appointment != null) {
                appointment.setBilling(this); // Establish the bidirectional link
            }
        }
    }
}
