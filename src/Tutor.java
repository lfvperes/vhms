/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.ArrayList;
import java.util.List;

class Tutor {
    private String name;
    private long id;
    private List<Patient> pets;
    private String email;
    private long phone;

    public Tutor(String name, long id, String email, long phone) {
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

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
