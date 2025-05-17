package com.clinic.appointment.controller;

import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService){
         this.doctorService = doctorService;
    }

    @GetMapping
    public String getDoctors(Model model){
        List<Doctor> doctorList = doctorService.findAll();
        model.addAttribute("doctors", doctorList);
        return "doctors/listing";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("doctor", new Doctor());
        return "doctors/create";
    }

    @PostMapping("/create")
    public String createDoctor(@ModelAttribute Doctor doctor){
        doctorService.create(doctor);
        return "redirect:/doctors";
    }
}
