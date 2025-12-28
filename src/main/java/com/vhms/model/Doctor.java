package com.vhms.model;
import com.vhms.utils.ValidationUtils;

import jakarta.persistence.Entity; // Import Entity
import jakarta.persistence.GeneratedValue; // Import GeneratedValue
import jakarta.persistence.GenerationType; // Import GenerationType
import jakarta.persistence.Id; // Import Id

/**
 *
 * @author lfvperes
 */
@Entity // Mark this class as a JPA entity
public class Doctor {
    private String name;
    private String specialization;
    @Id // Designate this as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate the ID
    private Long id; // Changed to Long for JPA compatibility
    private String email;
    private String phone;

    // Constructors
    public Doctor() {
    }

    // Removed the constructor that takes id and phone/email, as these will be managed by JPA or set later.
    public Doctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // Getter and setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        if (ValidationUtils.isValidPhone(phone)) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("The provided phone number is not a valid Brazilian mobile phone number: " + phone);
        }
    }
    
    public void setEmail(String email) {
        if (ValidationUtils.isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("The provided email address is invalid: " + email);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}

