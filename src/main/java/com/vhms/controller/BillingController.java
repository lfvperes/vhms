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

import com.vhms.model.Billing;
import com.vhms.service.HospitalService;

@RestController
@RequestMapping("/api/billings")
public class BillingController {

    private final HospitalService hospitalService;

    @Autowired
    public BillingController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public List<Billing> getAllBillings() {
        return hospitalService.getAllBillings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Billing> getBillingById(@PathVariable Long id) {
        Billing billing = hospitalService.getBillingById(id);
        if (billing != null) {
            return ResponseEntity.ok(billing);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Billing createBilling(@RequestBody Billing billing) {
        return hospitalService.addBilling(billing);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Billing> updateBilling(@PathVariable Long id, @RequestBody Billing billingDetails) {
        Billing existingBilling = hospitalService.getBillingById(id);
        if (existingBilling == null) {
            return ResponseEntity.notFound().build();
        }

        billingDetails.setId(id);
        Billing updatedBilling = hospitalService.addBilling(billingDetails);
        return ResponseEntity.ok(updatedBilling);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBilling(@PathVariable Long id) {
        Billing billing = hospitalService.getBillingById(id);
        if (billing != null) {
            hospitalService.deleteBillingById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}