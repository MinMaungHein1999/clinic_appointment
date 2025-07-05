package com.clinic.appointment.dto.patient;

import com.clinic.appointment.model.PatientType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class PatientCreateDto {
    private String name;
    private LocalDate dateOfBirth;
    private String email;
    private String address;
    private PatientType type;
    private MultipartFile file;
}
