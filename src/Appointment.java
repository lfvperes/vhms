/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.time.LocalDateTime;

/**
 *
 * @author lfvperes
 */
public class Appointment {
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Tutor tutor;
    private Billing billing;
    private String report;

    public Appointment(Patient patient, Doctor doctor, LocalDateTime startTime, LocalDateTime endTime, Tutor tutor, Billing billing) {
        if (startTime == null || endTime == null || endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("Appointment start time must be before end time.");
        }
        this.patient = patient;
        this.doctor = doctor;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        if (startTime == null || (this.endTime != null && this.endTime.isBefore(startTime))) {
            throw new IllegalArgumentException("Appointment start time must be before end time.");
        }
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        if (endTime == null || (this.startTime != null && endTime.isBefore(this.startTime))) {
            throw new IllegalArgumentException("Appointment end time must be after start time.");
        }
        this.endTime = endTime;
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
