package com.clinic.appointment.controller;

import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/update/{id}")
    public String submitUpdate(@PathVariable("id") Long id, @ModelAttribute("doctor")Doctor doctor){
        this.doctorService.update(id, doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        Doctor doctor = this.doctorService.findById(id);
        model.addAttribute("doctor", doctor);
        return "doctors/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id){
        this.doctorService.destory(id);
        return "redirect:/doctors";
    }

    @PostMapping("/create")
    public String createDoctor(@ModelAttribute("doctor") Doctor doctor){
        doctorService.create(doctor);
        return "redirect:/doctors";
    }
}
