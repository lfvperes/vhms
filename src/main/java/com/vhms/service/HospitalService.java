package com.vhms.service;

import com.vhms.model.Doctor;
import com.vhms.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HospitalService {
    // Using ConcurrentHashMap for thread-safe access
    private final ConcurrentHashMap<Long, Patient> patients = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Doctor> doctors = new ConcurrentHashMap<>();

    public void addPatient(Patient patient) {
        if (patient != null) {
            patients.put(patient.getId(), patient);
        }
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients.values());
    }

    public Patient getPatientById(Long id) {
        return patients.get(id);
    }

    public void addDoctor(Doctor doctor) {
        if (doctor != null) {
            doctors.put(doctor.getId(), doctor);
        }
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors.values());
    }

    public Doctor getDoctorById(Long id) {
        return doctors.get(id);
    }
}
