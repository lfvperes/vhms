package com.vhms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhms.model.Appointment;
import com.vhms.model.Billing;
import com.vhms.model.Doctor;
import com.vhms.model.Patient;
import com.vhms.model.Tutor;
import com.vhms.repository.AppointmentRepository;
import com.vhms.repository.BillingRepository;
import com.vhms.repository.DoctorRepository;
import com.vhms.repository.PatientRepository;
import com.vhms.repository.TutorRepository;

@Service
public class HospitalService {
    // Repositories
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository; // Inject Doctor repository
    @Autowired
    private TutorRepository tutorRepository;   // Inject Tutor repository
    @Autowired
    private AppointmentRepository appointmentRepository; // Inject Appointment repository
    @Autowired
    private BillingRepository billingRepository;     // Inject Billing repository

    // --- Patient Methods ---
    public Patient addPatient(Patient patient) {
        // JPA handles ID generation, so we just save the patient
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        // findById returns an Optional, so we use orElse(null)
        return patientRepository.findById(id).orElse(null);
    }

    public void deletePatientById(Long id) {
        patientRepository.deleteById(id);
    }

    // --- Doctor Methods (now using JPA Repository) ---
    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public void deleteDoctorById(Long id) {
        doctorRepository.deleteById(id);
    }

    // --- Tutor Methods ---
    public Tutor addTutor(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    public List<Tutor> getAllTutors() {
        return tutorRepository.findAll();
    }

    public Tutor getTutorById(Long id) {
        return tutorRepository.findById(id).orElse(null);
    }

    public void deleteTutorById(Long id) {
        tutorRepository.deleteById(id);
    }

    // --- Appointment Methods ---
    public Appointment addAppointment(Appointment appointment) {
        // When saving an appointment, ensure related entities (like Patient, Doctor) are persisted first
        // or are already managed by the persistence context.
        // If Patient, Doctor, etc., are passed as detached objects, they might need to be merged or persisted.
        // For simplicity here, assuming they are either new and managed, or already persisted.
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public void deleteAppointmentById(Long id) {
        appointmentRepository.deleteById(id);
    }

    // --- Billing Methods ---
    public Billing addBilling(Billing billing) {
        // Similar to Appointment, ensure related entities (like Appointment) are managed.
        return billingRepository.save(billing);
    }

    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }

    public Billing getBillingById(Long id) {
        return billingRepository.findById(id).orElse(null);
    }

    public void deleteBillingById(Long id) {
        billingRepository.deleteById(id);
    }
}
