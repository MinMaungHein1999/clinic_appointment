package com.clinic.appointment.service;

import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    public Doctor create(Doctor doctor){
        doctor = this.doctorRepository.save(doctor);
        return doctor;
    }

    public Doctor update(long id, Doctor doctor){
        Optional<Doctor> updateDoctorOp = this.doctorRepository.findById(id);
        if(!updateDoctorOp.isEmpty()) {
            Doctor updateDoctor = updateDoctorOp.get();
            updateDoctor.setName(doctor.getName());
            updateDoctor.setPhone(doctor.getPhone());
            updateDoctor.setAddress(doctor.getAddress());
            doctor = this.doctorRepository.save(updateDoctor);
            return doctor;
        }
        return null;
    }

    public Doctor findById(Long id){
        return this.doctorRepository.findById(id).orElseThrow();
    }

    public List<Doctor> findAll() {
        return this.doctorRepository.findAll();
    }

    public void destory(Long id) {
        Optional<Doctor> updateDoctorOp = this.doctorRepository.findById(id);
        if(!updateDoctorOp.isEmpty()) {
            this.doctorRepository.delete(updateDoctorOp.get());
        }
    }
}
