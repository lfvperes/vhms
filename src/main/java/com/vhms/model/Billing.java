package com.vhms.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = {jakarta.persistence.CascadeType.MERGE, jakarta.persistence.CascadeType.REFRESH})
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    // Constructors
    public Billing() {
    }

    // Constructor now only initializes Billing's own fields. 
    // The relationship will be managed via setters.
    public Billing(String description, BigDecimal amount, boolean paid) {
        this.description = description;
        this.amount = amount;
        this.paid = paid;
        // The appointment will be set using setAppointment() later.
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
        // Prevent infinite recursion if the object is already set correctly
        if (this.appointment == appointment) {
            return;
        }

        // If the current appointment is different from the new one
        if (this.appointment != null) {
            // Remove this billing from the old appointment's billing reference
            this.appointment.setBilling(null);
        }

        // Set the new appointment
        this.appointment = appointment;

        // If the new appointment is not null, set this billing on it
        if (appointment != null) {
            // This is the crucial part: ensure the appointment's billing is set to THIS Billing object.
            // The 'appointment.setBilling(this)' call here is generally safe because
            // 'this.appointment' has ALREADY been updated to 'appointment' above.
            // This means that if 'appointment.setBilling(this)' calls back to 'this.appointment.setBilling(null)',
            // it will be nulling out the OLD appointment's reference, not causing an infinite loop.
            appointment.setBilling(this);
        }
    }
}
