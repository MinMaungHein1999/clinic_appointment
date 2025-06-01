package com.clinic.appointment.service;

import com.clinic.appointment.expection.CommonException;
import com.clinic.appointment.expection.ErrorMessage;
import com.clinic.appointment.helper.StringUtil;
import com.clinic.appointment.model.Patient;
import com.clinic.appointment.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public void create(Patient patient, Model model) {
        List<ErrorMessage> errorMessages = new ArrayList<>();

        validateField(patient.getName(), "nameError", "Patient Name can't be empty", errorMessages);
        validateField(patient.getEmail(), "emailError", "Patient Email can't be empty", errorMessages);

        if(!errorMessages.isEmpty()){
            model.addAttribute("patient", patient);
            throw new CommonException(errorMessages, "patients/create", model);
        }

        patientRepository.save(patient);
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
            throw new CommonException(errorMessages, "patients/edit", model);
        }

        patientRepository.save(patient);
    }

    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }
}
