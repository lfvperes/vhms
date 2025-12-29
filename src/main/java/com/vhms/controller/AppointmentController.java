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

import com.vhms.model.Appointment;
import com.vhms.service.HospitalService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final HospitalService hospitalService;

    @Autowired
    public AppointmentController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return hospitalService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = hospitalService.getAppointmentById(id);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return hospitalService.addAppointment(appointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointmentDetails) {
        Appointment existingAppointment = hospitalService.getAppointmentById(id);
        if (existingAppointment == null) {
            return ResponseEntity.notFound().build();
        }

        appointmentDetails.setId(id);
        Appointment updatedAppointment = hospitalService.addAppointment(appointmentDetails);
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        Appointment appointment = hospitalService.getAppointmentById(id);
        if (appointment != null) {
            hospitalService.deleteAppointmentById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}