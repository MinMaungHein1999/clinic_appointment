package com.clinic.appointment.repository;

import com.clinic.appointment.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByAppUserId(Long id);
}
