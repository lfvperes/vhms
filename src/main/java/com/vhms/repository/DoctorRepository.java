package com.vhms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vhms.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {}
