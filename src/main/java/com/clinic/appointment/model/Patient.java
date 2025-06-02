package com.clinic.appointment.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "patients")
public class Patient {
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String email;
    private String address;
    @Enumerated(EnumType.STRING)
    private PatientType type;
}
