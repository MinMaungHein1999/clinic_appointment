package com.clinic.appointment.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@Controller
public class HomeController {
    @GetMapping("/select-role")
    public String selectRole(Model model, HttpSession session){
        List<String> userRoles = (List<String>) session.getAttribute("userRoles");
        if(userRoles == null || userRoles.isEmpty()){
            return "redirect:/";
        }
        model.addAttribute("roles", userRoles);
        return "select-role";
    }

    @PostMapping("/set-active-role")
    public String setActiveRole(@RequestParam("selectedRole") String selectedRole, HttpSession session, Authentication authentication){
        Collection<? extends GrantedAuthority> userRoles = authentication.getAuthorities();
        boolean isValidRole = userRoles.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equalsIgnoreCase(selectedRole));

        if(isValidRole){
            session.setAttribute("activeRole", selectedRole);
            if("ROLE_ADMIN".equalsIgnoreCase(selectedRole)){
                return "redirect:/admins/dashboard";
            }else if("ROLE_DOCTOR".equalsIgnoreCase(selectedRole)){
                return "redirect:/doctors/dashboard";
            }else if("ROLE_PATIENT".equalsIgnoreCase(selectedRole)){
                return "redirect:/patients/home";
            }
        }

        return "redirect:/select-role?error=invalid_selection";
    }
}
