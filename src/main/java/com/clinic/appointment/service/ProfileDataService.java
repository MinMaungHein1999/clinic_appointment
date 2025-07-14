package com.clinic.appointment.service;

import com.clinic.appointment.dto.ProfileDto;
import com.clinic.appointment.model.Admin;
import com.clinic.appointment.model.AppUser;
import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.model.Patient;
import com.clinic.appointment.model.constant.FileType;
import com.clinic.appointment.repository.AdminRepository;
import com.clinic.appointment.repository.DoctorRepository;
import com.clinic.appointment.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileDataService {

    @Autowired
    private final AuthService authService;
    @Autowired
    private final DoctorRepository doctorRepository;
    @Autowired
    private final PatientRepository patientRepository;
    @Autowired
    private final AdminRepository adminRepository;
    @Autowired
    private final FileService fileService;


    public ProfileDto getProfile(){
        AppUser currentUser  = authService.getCurrentUser();
        String activeRole = authService.getActiveRole();
        if(currentUser == null || activeRole == null){
            return null;
        }
        ProfileDto profileDto = new ProfileDto();

        Admin admin = null;
        Patient patient = null;
        Doctor doctor = null;

        if(activeRole.equalsIgnoreCase("ROLE_ADMIN")){
            admin = this.adminRepository.findByAppUserId(currentUser.getId()).orElse(null);
            profileDto.setName(admin.getName());
            String profileUrl = fileService.getFileName(FileType.ADMIN, admin.getId());
            profileDto.setUrl(profileUrl);
        }else if(activeRole.equalsIgnoreCase("ROLE_DOCTOR")){
            doctor = this.doctorRepository.findByAppUserId(currentUser.getId()).orElse(null);
            profileDto.setName(doctor.getName());
            String profileUrl = fileService.getFileName(FileType.DOCTOR, doctor.getId());
            profileDto.setUrl(profileUrl);
        }else if(activeRole.equalsIgnoreCase("ROLE_PATIENT")){
            patient = this.patientRepository.findByAppUserId(currentUser.getId()).orElse(null);
            profileDto.setName(patient.getName());
            String profileUrl = fileService.getFileName(FileType.PATIENT, patient.getId());
            profileDto.setUrl(profileUrl);
        }
        profileDto.setEmail(currentUser.getEmail());
        profileDto.setActiveRole(activeRole);

        return profileDto;
    }

}
