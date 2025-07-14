package com.clinic.appointment.repository;

import com.clinic.appointment.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByAppUserId(Long id);
}
