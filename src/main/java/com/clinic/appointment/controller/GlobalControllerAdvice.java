package com.clinic.appointment.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("requestURI")
    public String  requestURL(HttpServletRequest request){
       return request.getRequestURI();
    }
}
