package com.clinic.appointment.controller;

import com.clinic.appointment.model.AppUser;
import com.clinic.appointment.repository.DoctorRepository;
import com.clinic.appointment.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private AuthService authService;
    @Autowired
    private  DoctorRepository doctorRepository;


    @GetMapping("/dashboard")
    public String adminDashboard(Model model){
        AppUser appUser = authService.getCurrentUser();
        model.addAttribute("profile", doctorRepository.findByAppUserId(appUser.getId()));
        model.addAttribute("sideBarTitle", "Admin Dashboard");
        return "admins/dashboard/index";
    }
}
