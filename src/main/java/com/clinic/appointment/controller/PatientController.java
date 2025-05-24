package com.clinic.appointment.controller;

import com.clinic.appointment.model.Patient;
import com.clinic.appointment.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patients/create";
    }

    @PostMapping("/create")
    public String createPatient(@ModelAttribute Patient patient) {
        patientService.create(patient);
        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Patient patient = patientService.findById(id);
        if (patient == null) return "redirect:/patients";
        model.addAttribute("patient", patient);
        return "patients/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient) {
        patient.setId(id);
        patientService.update(patient);
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deleteById(id);
        return "redirect:/patients";
    }
}
