package com.clinic.appointment.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String phone;
    private String address;
}
