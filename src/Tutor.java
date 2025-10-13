/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
        // validation for a Brazilian mobile phone number
        String phoneRegex = "^[1-9]{2}9{1}[0-9]{8}$";
        if (phone != null && phone.matches(phoneRegex)) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("The provided phone number is not a valid Brazilian mobile phone number: " + phone);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    
        Pattern p = Pattern.compile(emailRegex);
        if (email != null && p.matcher(email).matches()) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("The provided email address is invalid: " + email);
        }
    }
}
