package com.vhms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vhms.model.Patient;

@Repository // Marks this as a Spring Data repository
public interface PatientRepository extends JpaRepository<Patient, Long> {}
