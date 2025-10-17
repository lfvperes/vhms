package com.vhms.model;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.vhms.utils.ValidationUtils;
import java.util.ArrayList;
import java.util.List;

public class Tutor {
    private String name;
    private long id;
    private List<Patient> pets;
    private String email;
    private String phone;

    public Tutor(String name, long id, String email, String phone) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.pets = new ArrayList<>(pets);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Patient> getPets() {
        return pets;
    }

    public void addPet(Patient patient) {
        this.pets.add(patient);
    }

    public void removePet(Patient patient) {
        this.pets.remove(patient);
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
