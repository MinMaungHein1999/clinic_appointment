package com.clinic.appointment.dto.doctor;

import lombok.Data;

@Data
public class DoctorDto {

    private Long id;
    private String name;
    private int age;
    private String phone;
    private String address;
    private String profileUrl;

}
