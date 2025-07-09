package com.clinic.appointment.controller;

import com.clinic.appointment.dto.patient.PatientCreateDto;
import com.clinic.appointment.dto.patient.PatientDto;
import com.clinic.appointment.model.Patient;
import com.clinic.appointment.model.PatientType;
import com.clinic.appointment.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.findAll());
        return "patients/listing";
    }

    @GetMapping("/home")
    public String doctorDashboard(Model model){
        model.addAttribute("sideBarTitle", "Patient Home");
        return "patients/home/index";
    }

    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable("id") Long id, Model model){
        PatientDto patientDto= patientService.findById(id);
        model.addAttribute("patient" , patientDto);
        return "patients/profile";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("patient", new PatientCreateDto());
        model.addAttribute("patientTypes", PatientType.values());
        return "patients/create";
    }

    @PostMapping("/create")
    public String createPatient(@ModelAttribute PatientCreateDto createDto, Model model) {
            model.addAttribute("patient", createDto);
            patientService.create(createDto, model);
            return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        PatientDto patient = patientService.findById(id);
        if (patient == null) return "redirect:/patients";
        model.addAttribute("patient", patient);
        return "patients/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient, Model model) {
        patient.setId(id);
        patientService.update(patient, model);
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deleteById(id);
        return "redirect:/patients";
    }
}
