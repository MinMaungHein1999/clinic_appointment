package com.clinic.appointment.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class DoctorCreateDto {
    private String name;
    private LocalDate dob;
    private String phone;
    private String address;
    private MultipartFile file;
}
