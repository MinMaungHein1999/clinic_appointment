package com.clinic.appointment.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if(roles.size() > 1){
            request.getSession().setAttribute("userRoles", new ArrayList<>(roles));
            response.sendRedirect("/select-role");
        }else if(roles.contains("ADMIN")){
            response.sendRedirect("/admins/dashboard");
        }else if(roles.contains("PATIENT")){
            response.sendRedirect("/patients/home");
        } else if(roles.contains("DOCTOR")){
            response.sendRedirect("/doctors/dashboard");
        } else {
            response.sendRedirect("/");
        }
    }
}
