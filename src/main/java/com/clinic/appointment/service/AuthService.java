package com.clinic.appointment.service;

import com.clinic.appointment.model.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public AppUser getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof AppUser){

            AppUser appUser = (AppUser) authentication.getPrincipal();
            return appUser;
        }
        return null;
    }
}
