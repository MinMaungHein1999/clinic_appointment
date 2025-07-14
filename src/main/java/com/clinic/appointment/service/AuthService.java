package com.clinic.appointment.service;

import com.clinic.appointment.model.AppUser;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class AuthService {

    public AppUser getCurrentUser(){
        ServletRequestAttributes attr  = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        AppUser appUser = null;
        if (session != null) {
            appUser = (AppUser) session.getAttribute("currentUser");
        }
        return appUser;
    }

    public String getActiveRole() {
        ServletRequestAttributes attr  = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        String activeRole = null;
        if(session != null){
            activeRole =(String) session.getAttribute("activeRole");
        }
        return activeRole;
    }
}
