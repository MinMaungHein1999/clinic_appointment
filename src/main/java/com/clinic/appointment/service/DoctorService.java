package com.clinic.appointment.service;

import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.repository.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor create(Doctor doctor){
        doctor = this.doctorRepository.save(doctor);
        return doctor;
    }

    public Doctor update(Doctor doctor){
        doctor = this.doctorRepository.save(doctor);
        return doctor;
    }

    public Doctor findById(Long id){
        return this.doctorRepository.findById(id).orElseThrow();
    }

}
