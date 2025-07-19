package com.clinic.appointment.api.v1.controller;

import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("doctorControllerV1")
@RequestMapping("/api/v1")
public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;

    @RequestMapping("/doctors")
    public ResponseEntity<List<Doctor>> getDoctors(){
        List<Doctor> doctors  = doctorRepository.findAll();
        return ResponseEntity.ok(doctors);
    }
}
