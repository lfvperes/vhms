/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.regex.Pattern;

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
        // validation for a Brazilian mobile phone number
        String phoneRegex = "^[1-9]{2}9{1}[0-9]{8}$";
        if (phone != null && phone.matches(phoneRegex)) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("The provided phone number is not a valid Brazilian mobile phone number: " + phone);
        }
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

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
