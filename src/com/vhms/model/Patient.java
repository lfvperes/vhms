import java.util.Collections;package com.vhms.model;
import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String name;
    private byte age;
    private Tutor tutor;
    private Species species;
    private long id;
    private boolean microchip;
    private boolean insurance;
    private final List<Appointment> medicalHistory;
    private boolean sex;
    private float weight;
    private String breed;

    public Patient(byte age, String breed, long id, boolean insurance, boolean microchip, String name, boolean sex, Species species, Tutor tutor, float weight) {
        this.age = age;
        this.breed = breed;
        this.id = id;
        this.insurance = insurance;
        this.microchip = microchip;
        this.name = name;
        this.sex = sex;
        this.species = species;
        this.tutor = tutor;
        this.weight = weight;
        this.medicalHistory = new ArrayList<>();
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
            // Throw and exception to signal that this is an invalid operation
            throw new IllegalArgumentException("Age must be positive.");
        }
        this.age = newAge;
    }
    public Tutor getTutor() {
        return tutor;
    }
    public void changeTutor(Tutor oldTutor, Tutor newTutor) {
        oldTutor.removePet(this);
        newTutor.addPet(this);
        this.tutor = newTutor;
    }
    public Species getSpecies() {
        return species;
    }
    public void setSpecies(Species species) {
        this.species = species;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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
    public List<Appointment> getMedicalHistory() {
        return Collections.unmodifiableList(medicalHistory);
    }

    public void addAppointmentToHistory(Appointment appointment) {
        this.medicalHistory.add(appointment);
    }

    public void removeAppointmentFromHistory(Appointment appointment) {
        this.medicalHistory.remove(appointment);
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
            // Throw an exception to signal that this is an invalid operation
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
