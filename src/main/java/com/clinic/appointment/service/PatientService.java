package com.clinic.appointment.service;

import com.clinic.appointment.dto.patient.PatientCreateDto;
import com.clinic.appointment.dto.patient.PatientDto;
import com.clinic.appointment.expection.CommonException;
import com.clinic.appointment.expection.ErrorMessage;
import com.clinic.appointment.helper.StringUtil;
import com.clinic.appointment.model.Patient;
import com.clinic.appointment.model.constant.FileType;
import com.clinic.appointment.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private FileService fileService;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public PatientDto findById(Long id) {
        Patient patient = patientRepository.findById(id).orElse(null);
        PatientDto patientDto = new PatientDto();
        patientDto.setId(patient.getId());
        patientDto.setName(patient.getName());
        patientDto.setEmail(patient.getEmail());
        patientDto.setDateOfBirth(patient.getDateOfBirth());
        patientDto.setType(patient.getType());

        String url = fileService.getFileName(FileType.PATIENT, patient.getId());
        patientDto.setFileUrl(url);
        return patientDto;
    }

    public void create(PatientCreateDto patientDto, Model model) {
        List<ErrorMessage> errorMessages = new ArrayList<>();

        validateField(patientDto.getName(), "nameError", "Patient Name can't be empty", errorMessages);
        validateField(patientDto.getEmail(), "emailError", "Patient Email can't be empty", errorMessages);

        if(!errorMessages.isEmpty()){
            model.addAttribute("patient", patientDto);
            throw new CommonException(errorMessages, "patients/create", model);
        }

        Patient patient = new Patient();
        patient.setName(patientDto.getName());
        patient.setEmail(patientDto.getEmail());
        patient.setAddress(patientDto.getAddress());
        patient.setDateOfBirth(patientDto.getDateOfBirth());
        patient.setType(patientDto.getType());

        patient = patientRepository.save(patient);

        fileService.handleFileUpload(patientDto.getFile(), FileType.PATIENT, patient.getId(), "local");
    }

    private void validateField(String value, String fieldName, String message, List<ErrorMessage> errorMessageList){
        if(StringUtil.isEmpty(value)){
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setFiledName(fieldName);
            errorMessage.setMessage(message);
            errorMessageList.add(errorMessage);
        }
    }

    public void update(Patient patient, Model model) {
        List<ErrorMessage> errorMessages = new ArrayList<>();

        validateField(patient.getName(), "nameError", "Patient Name can't be empty", errorMessages);
        validateField(patient.getEmail(), "emailError", "Patient Email can't be empty", errorMessages);
        if(!errorMessages.isEmpty()){
            model.addAttribute("patient", patient);
            throw new CommonException(errorMessages, "patients/edit".formatted(patient.getId()), model);
        }

        patientRepository.save(patient);
    }

    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }
}
