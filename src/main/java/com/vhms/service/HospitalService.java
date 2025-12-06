package com.vhms.service;

import com.vhms.model.Doctor;
import com.vhms.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class HospitalService {
    // Using ConcurrentHashMap for thread-safe access
    private final ConcurrentHashMap<Long, Patient> patients = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Doctor> doctors = new ConcurrentHashMap<>();
    private final AtomicLong patientIdCounter = new AtomicLong();
    private final AtomicLong doctorIdCounter = new AtomicLong();

    public Patient addPatient(Patient patient) {
        if (patient != null) {
            long newId = patientIdCounter.incrementAndGet();
            patient.setId(newId);
            patients.put(newId, patient);
            return patient;
        }
        return null;
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients.values());
    }

    public Patient getPatientById(Long id) {
        return patients.get(id);
    }

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
        return new ArrayList<>(doctors.values());
    }

    public Doctor getDoctorById(Long id) {
        return doctors.get(id);
    }
}
