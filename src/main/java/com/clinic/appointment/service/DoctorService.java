package com.clinic.appointment.service;

import com.clinic.appointment.dto.doctor.DoctorCreateDto;
import com.clinic.appointment.dto.doctor.DoctorDto;
import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.model.constant.FileType;
import com.clinic.appointment.repository.DoctorRepository;
import com.clinic.appointment.util.AgeCalculator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorService {
    private final FileService fileService;
    private final DoctorRepository doctorRepository;

    public void create(DoctorCreateDto createDto){

        Doctor doctor = new Doctor();

        doctor.setName(createDto.getName());
        doctor.setDob(createDto.getDob());
        doctor.setAddress(createDto.getAddress());
        doctor.setPhone(createDto.getPhone());
        doctor = this.doctorRepository.save(doctor);

        fileService.handleFileUpload(createDto.getFile(), FileType.DOCTOR,doctor.getId(), "dev" );
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
        int age = 0;
        try {
            age = AgeCalculator.calculateAge(doctor.getDob());
        }catch (IllegalArgumentException ex){

        }

        doctorDto.setId(doctor.getId());
        doctorDto.setName(doctor.getName());
        doctorDto.setAge(age);
        doctorDto.setPhone(doctor.getPhone());
        doctorDto.setAddress(doctor.getAddress());
        doctorDto.setProfileUrl(this.fileService.getFileName(FileType.DOCTOR, doctor.getId()));

        return doctorDto;
    }

    public void deleteById(Long id) {
        doctorRepository.deleteById(id);
    }

}
