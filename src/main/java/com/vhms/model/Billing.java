package com.vhms.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table; // Import BigDecimal

@Entity
@Table(name = "billings") // Specify the name of the database table
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true) // Specify the column name and properties
    private Long id;

    @Column(name = "description", length = 255) // Specify the column name and length
    private String description;

    // Changed from double to BigDecimal for monetary values
    @Column(name = "amount", precision = 10, scale = 2) // Specify the column name, precision, and scale
    private BigDecimal amount;

    @Column(name = "paid") // Specify the column name
    private boolean paid;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false) // Specify the join column name and properties
    private Appointment appointment;

    // Constructors
    public Billing() {
    }

    // Updated constructor to use BigDecimal
    public Billing(String description, BigDecimal amount, boolean paid, Appointment appointment) {
        this.description = description;
        this.amount = amount;
        this.paid = paid;
        this.appointment = appointment;
    }

    // Getters and setters for id, description
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
        this.appointment = appointment;
    }
}
