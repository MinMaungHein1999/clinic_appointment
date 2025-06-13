package com.clinic.appointment.service;

import com.clinic.appointment.dto.DoctorDto;
import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.repository.DoctorRepository;
import com.clinic.appointment.util.AgeCalculator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public void create(Doctor doctor){

        doctor = this.doctorRepository.save(doctor);
    }

    public Doctor update(long id,Doctor doctor) {
        Optional<Doctor> updateDoctorOp = this.doctorRepository.findById(id);
        if (!updateDoctorOp.isEmpty()) {
            Doctor updateDoctor = updateDoctorOp.get();
            updateDoctor.setName(doctor.getName());
            updateDoctor.setAddress(doctor.getAddress());
            updateDoctor.setPhone(doctor.getPhone());
            doctor = this.doctorRepository.save(updateDoctor);
            return doctor;
        }
        return null;
    }

    public List<Doctor> findAll(){
        return this.doctorRepository.findAll();
    }

    public DoctorDto findById(Long id){
        Doctor doctor = this.doctorRepository.findById(id).orElseThrow();

        DoctorDto doctorDto = new DoctorDto();
        int age = AgeCalculator.calculateAge(doctor.getDob());

        doctorDto.setId(doctor.getId());
        doctorDto.setName(doctor.getName());
        doctorDto.setAge(age);
        doctorDto.setPhone(doctor.getPhone());
        doctorDto.setAddress(doctor.getAddress());

        return doctorDto;
    }

    public void deleteById(Long id) {
        doctorRepository.deleteById(id);
    }

}
