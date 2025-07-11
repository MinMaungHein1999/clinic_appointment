package com.clinic.appointment.dto.patient;

import com.clinic.appointment.model.constant.PatientType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String email;
    private String address;
    private PatientType type;
    private String fileUrl;
}
