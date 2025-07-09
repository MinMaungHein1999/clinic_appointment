package com.clinic.appointment.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service("activeRoleService")
public class ActiveRoleService {
    public boolean hasActiveRole(String requiredRole){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return false;
        }

        ServletRequestAttributes attr  = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);

        if(session != null){
            String activeRole =(String) session.getAttribute("activeRole");
            return activeRole != null && activeRole.equalsIgnoreCase(requiredRole);
        }
        return true;
    }
}
