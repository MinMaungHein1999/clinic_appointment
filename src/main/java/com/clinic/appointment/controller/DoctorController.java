package com.clinic.appointment.controller;

import com.clinic.appointment.dto.DoctorDto;
import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping
    public String getDoctors(Model model){
        model.addAttribute("doctors" , doctorService.findAll());
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


    //when hit edit button in listing.html
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        DoctorDto doctorDto = this.doctorService.findById(id);
        if (doctorDto == null) {
            // handle not found case (optional)
            return "redirect:/doctors";
        }
        model.addAttribute("doctor", doctorDto);
        return "doctors/edit"; // This should be your edit form view
    }

    //when hit the update button
    @PostMapping("/update/{id}")
    public String updateDoctor(@ModelAttribute("doctor") Doctor doctor , @PathVariable("id") Long id) {
        doctorService.update(id ,doctor);
        return "redirect:/doctors";
    }

    //for deleting
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id") Long id) {
        doctorService.deleteById(id);
        return "redirect:/doctors";
    }






}
