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

import com.vhms.model.Tutor;
import com.vhms.service.HospitalService;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {

    private final HospitalService hospitalService;

    @Autowired
    public TutorController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public List<Tutor> getAllTutors() {
        return hospitalService.getAllTutors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutor> getTutorById(@PathVariable Long id) {
        Tutor tutor = hospitalService.getTutorById(id);
        if (tutor != null) {
            return ResponseEntity.ok(tutor);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Tutor createTutor(@RequestBody Tutor tutor) {
        return hospitalService.addTutor(tutor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tutor> updateTutor(@PathVariable Long id, @RequestBody Tutor tutorDetails) {
        Tutor existingTutor = hospitalService.getTutorById(id);
        if (existingTutor == null) {
            return ResponseEntity.notFound().build();
        }

        tutorDetails.setId(id);
        Tutor updatedTutor = hospitalService.addTutor(tutorDetails);
        return ResponseEntity.ok(updatedTutor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutor(@PathVariable Long id) {
        Tutor tutor = hospitalService.getTutorById(id);
        if (tutor != null) {
            hospitalService.deleteTutorById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}