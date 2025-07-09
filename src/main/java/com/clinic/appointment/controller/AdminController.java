package com.clinic.appointment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/dashboard")
    public String doctorDashboard(Model model){
        model.addAttribute("sideBarTitle", "Admin Dashboard");
        return "admins/dashboard/index";
    }
}
