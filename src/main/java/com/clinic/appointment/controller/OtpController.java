package com.clinic.appointment.controller;

import com.clinic.appointment.model.AppUser;
import com.clinic.appointment.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class OtpController {

    private final AppUserRepository appUserRepository;

    @GetMapping("/confirm-account/otp")
    public String showConfirmAccountOtp(Model model, @RequestParam("email") String email){
        model.addAttribute("email", email);
        return "/confirm-account/otp";
    }

    @PostMapping("/confirm-account/verify-otp")
    public String verifyConfirmOtp(@RequestParam("email") String email, @RequestParam("otp") String otp ){
        if(otp.equalsIgnoreCase("78789")){
            AppUser appUser  = appUserRepository.findByEmail(email).orElse(null);
            appUser.setConfirmedAt(LocalDate.now());
            appUserRepository.save(appUser);
            return "/login";
        }
        return "/confirm-account/otp";
    }
}
