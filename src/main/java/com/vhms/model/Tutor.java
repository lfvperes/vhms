package com.vhms.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vhms.utils.ValidationUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;

    // One tutor can have many patients
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Patient> pets = new ArrayList<>();

    // Constructors
    public Tutor() {
    }
    public Tutor(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Patient> getPets() {
        return Collections.unmodifiableList(pets);
    }

    public void addPet(Patient patient) {
        if (patient != null) {
        this.pets.add(patient);
            patient.setTutor(this); // Crucial: set the tutor on the patient side
    }
    }

    public void removePet(Patient patient) {
        if (patient != null) {
            this.pets.remove(patient);
            patient.setTutor(null); // Break the link on the patient side
    }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (ValidationUtils.isValidPhone(phone)) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("The provided phone number is not a valid Brazilian mobile phone number: " + phone);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (ValidationUtils.isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("The provided email address is invalid: " + email);
        }
    }
}

