package com.vhms.controller;

import com.vhms.model.Patient;
import com.vhms.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final HospitalService hospitalService;

    @Autowired
    public PatientController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return hospitalService.getAllPatients();
    }
}
