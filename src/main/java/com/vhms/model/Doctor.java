package com.vhms.model;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.vhms.utils.ValidationUtils;

/**
 *
 * @author lfvperes
 */
public class Doctor {
    private String name;
    private String specialization;
    private long id;
    private String email;
    private String phone;

    public Doctor(String email, long id, String name, String phone, String specialization) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.phone = phone;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
