package com.clinic.appointment.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.clinic.appointment.dto.doctor.DoctorCreateDto;
import com.clinic.appointment.dto.doctor.DoctorDto;
import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.model.constant.FileType;
import com.clinic.appointment.model.constant.Status;
import com.clinic.appointment.repository.DoctorRepository;
import com.clinic.appointment.util.AgeCalculator;
import lombok.AllArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorService {
    private final FileService fileService;
    private final DoctorRepository doctorRepository;
    private final MasterDataPolicy masterDataPolicy;

    @PreAuthorize("@masterDataPolicy.canCreate(#doctor)")
    public void create(DoctorCreateDto createDto){

        Doctor doctor = new Doctor();

        doctor.setName(createDto.getName());
        doctor.setDob(createDto.getDob());
        doctor.setAddress(createDto.getAddress());
        doctor.setPhone(createDto.getPhone());
        doctor = this.doctorRepository.save(doctor);

        fileService.handleFileUpload(createDto.getFile(), FileType.DOCTOR,doctor.getId(), "dev" );
    }

    @PreAuthorize("@masterDataPolicy.canUpdate(#updateDoctor)")
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

    @PreAuthorize("@masterDataPolicy.canView(filterObject)")
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
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if(doctor==null){
            throw new NotFoundException("Doctor Id not found for : "+id);
        }
        if(!masterDataPolicy.canDelete(doctor)){
            throw new RuntimeException("Can't delete doctor");
        }
        doctor.delete();
        doctorRepository.save(doctor);
    }

}
