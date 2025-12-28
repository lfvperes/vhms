package com.vhms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vhms.model.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {}
