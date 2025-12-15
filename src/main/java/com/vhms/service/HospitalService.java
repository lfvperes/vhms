package com.vhms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhms.model.Doctor;
import com.vhms.model.Patient;
import com.vhms.repository.PatientRepository;

@Service
public class HospitalService {
    // Using ConcurrentHashMap for thread-safe access for Doctors
    private final ConcurrentHashMap<Long, Doctor> doctors = new ConcurrentHashMap<>();
    private final AtomicLong doctorIdCounter = new AtomicLong(); // Still needed for Doctor for now

    // Inject the PatientRepository
    @Autowired
    private PatientRepository patientRepository;

    // Inject DoctorRepository if Doctor is also made JPA entity
    // @Autowired
    // private DoctorRepository doctorRepository;

    // Constructor for HospitalService (if needed, otherwise Spring's default is fine)
    // public HospitalService() {} // Not strictly needed if no explicit fields require initialization here

    public Patient addPatient(Patient patient) {
        // JPA handles ID generation, so we just save the patient
        // The save method will return the patient with its generated ID
        if (patient != null) {
            // Ensure the patient object doesn't have an ID set before saving,
            // as JPA will generate it. Setting it to null explicitly can help.
            // However, since we updated constructors to not pass ID, this should be fine.
            return patientRepository.save(patient);
        }
        return null;
    }

    // Use repository for getting all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        // Use repository for finding by ID
        // findById returns an Optional, so we use orElse(null)
        return patientRepository.findById(id).orElse(null);
    }

    // --- Doctor Methods (still using old in-memory approach for now) ---
    public Doctor addDoctor(Doctor doctor) {
        if (doctor != null) {
            long newId = doctorIdCounter.incrementAndGet();
            doctor.setId(newId);
            doctors.put(newId, doctor);
            return doctor;
        }
        return null;
    }

    public List<Doctor> getAllDoctors() {
        // Return a copy to prevent external modification of the internal map
        return new ArrayList<>(doctors.values());
    }

    public Doctor getDoctorById(Long id) {
        return doctors.get(id);
    }

    // Potential future method if Doctor becomes a JPA entity:
    // @Autowired
    // private DoctorRepository doctorRepository;
    // public Doctor addDoctor(Doctor doctor) {
    //     return doctorRepository.save(doctor);
    // }
    // public List<Doctor> getAllDoctors() {
    //     return doctorRepository.findAll();
    // }
    // public Doctor getDoctorById(Long id) {
    //     return doctorRepository.findById(id).orElse(null);
    // }
}
