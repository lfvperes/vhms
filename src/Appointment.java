/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lfvperes
 */
public class Appointment {
    private Patient patient;
    private Doctor doctor;
    private String date;
    private Tutor tutor;
    private Billing billing;
    private String report;

    public Appointment(Patient patient, Doctor doctor, String date, Tutor tutor, Billing billing) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.tutor = tutor;
        this.billing = billing;
        this.report = ""; // Default empty report
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
