package com.clinic.appointment.config;

import com.clinic.appointment.service.ProfileDataService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class RequestURIInterceptor implements HandlerInterceptor {

    @Autowired
    private final ProfileDataService profileDataService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if(modelAndView != null){
            modelAndView.addObject("profileData", profileDataService.getProfile());
            modelAndView.addObject("requestURI", request.getRequestURI());
        }
    }
}
