package com.vhms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vhms.model.Doctor;
import com.vhms.service.HospitalService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final HospitalService hospitalService;

    @Autowired
    public DoctorController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return hospitalService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = hospitalService.getDoctorById(id);
        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return hospitalService.addDoctor(doctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails) {
        // Check if doctor exists
        Doctor existingDoctor = hospitalService.getDoctorById(id);
        if (existingDoctor == null) {
            return ResponseEntity.notFound().build();
        }

        // Set the ID to ensure update, not create
        doctorDetails.setId(id);

        // Save updated doctor
        Doctor updatedDoctor = hospitalService.addDoctor(doctorDetails);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        Doctor doctor = hospitalService.getDoctorById(id);
        if (doctor != null) {
            hospitalService.deleteDoctorById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}