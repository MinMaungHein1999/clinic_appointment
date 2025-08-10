package com.clinic.appointment.api.v1.controller;

import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("doctorControllerV1")
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;

    @RequestMapping()
    public ResponseEntity<List<Doctor>> getDoctors(){
        List<Doctor> doctors  = doctorRepository.findAll();
        return ResponseEntity.ok(doctors);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Doctor doctor){
        doctor = this.doctorRepository.save(doctor);
        return new ResponseEntity<>(doctor, HttpStatus.CREATED);
    }
}
