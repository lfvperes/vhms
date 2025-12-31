package com.vhms.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // Import OneToMany
import jakarta.persistence.JoinColumn; // Import ManyToOne
import jakarta.persistence.ManyToOne; // Import JoinColumn
import jakarta.persistence.OneToMany; // Import CascadeType for relationship management

@Entity
public abstract class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private byte age;

    // @ManyToOne annotation for the Tutor relationship
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    private Species species;
    private boolean microchip;
    private boolean insurance;

    // @OneToMany annotation for the Appointment relationship
    // mappedBy refers to the 'patient' field in the Appointment class
    // CascadeType.ALL means operations on Patient (like delete) will cascade to Appointments
    // orphanRemoval = true means if an Appointment is removed from the list, it's deleted from the DB
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Appointment> medicalHistory = new ArrayList<>(); // Initialize directly

    private boolean sex;
    private float weight;
    private String breed;

    // Constructor updated: removed 'long id' as it's auto-generated
    public Patient(byte age, String breed, boolean insurance, boolean microchip, String name, boolean sex, Species species, Tutor tutor, float weight) {
        this.age = age;
        this.breed = breed;
        this.insurance = insurance;
        this.microchip = microchip;
        this.name = name;
        this.sex = sex;
        this.species = species;
        this.tutor = tutor;
        this.weight = weight;
        // medicalHistory is initialized directly above
    }

    // Default constructor for JPA (required)
    public Patient() {
        // medicalHistory is initialized directly above
    }

    // --- Getters and Setters ---

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
    public byte getAge() {
        return age;
    }
    public void setAge(byte newAge) {
        if (newAge <= 0) {
            throw new IllegalArgumentException("Age must be positive.");
        }
        this.age = newAge;
    }

    public Tutor getTutor() {
        return tutor;
    }
    public void setTutor(Tutor tutor) {
        if (this.tutor!= null) {
            this.tutor.removePet(this);
        }
        this.tutor = tutor;
        if (tutor!= null) {
            tutor.addPet(this);
        }
    }

    // Method to manage the relationship for Tutor
    // This helps keep both sides of the relationship synchronized
    public void changeTutor(Tutor oldTutor, Tutor newTutor) {
        if (oldTutor != null) {
            oldTutor.removePet(this); // Assuming Tutor has removePet method
        }
        if (newTutor != null) {
            newTutor.addPet(this); // Assuming Tutor has addPet method
            this.tutor = newTutor;
        } else {
            this.tutor = null; // Handle case where tutor is being removed
        }
    }

    public Species getSpecies() {
        return species;
    }
    public void setSpecies(Species species) {
        this.species = species;
    }

    public boolean hasMicrochip() {
        return microchip;
    }
    public void addMicrochip() {
        if (microchip) {
            throw new IllegalArgumentException("Patient already has a microchip.");
        } else {
            this.microchip = true;
        }
    }
    public boolean hasInsurance() {
        return insurance;
    }
    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    // This getter returns an unmodifiable list to prevent direct modification
    public List<Appointment> getMedicalHistory() {
        return Collections.unmodifiableList(medicalHistory);
    }

    // Helper methods to manage the bidirectional relationship with Appointment
    public void addAppointmentToHistory(Appointment appointment) {
        if (appointment != null) {
            this.medicalHistory.add(appointment);
            appointment.setPatient(this); // Crucial: set the patient on the appointment side
        }
    }

    public void removeAppointmentFromHistory(Appointment appointment) {
        if (appointment != null) {
            this.medicalHistory.remove(appointment);
            appointment.setPatient(null); // Break the link on the appointment side
        }
    }

    public boolean isMale() {
        return sex;
    }
    public void setSex(boolean sex) {
        this.sex = sex;
    }
    public float getWeight() {
        return weight;
    }
    
    public void setWeight(float newWeight) {
        if (newWeight <= 0) {
            throw new IllegalArgumentException("Weight must be positive.");
        }
        this.weight = newWeight;
    }
    
    public String getBreed() {
        return breed;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }
}
