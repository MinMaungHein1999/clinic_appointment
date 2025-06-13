package com.clinic.appointment.dto;

import lombok.Data;

@Data
public class DoctorDto {

    private Long id;
    private String name;
    private int age;
    private String phone;
    private String address;

}
