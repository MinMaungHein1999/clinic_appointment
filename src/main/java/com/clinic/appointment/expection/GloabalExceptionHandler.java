package com.clinic.appointment.expection;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GloabalExceptionHandler {

    @ModelAttribute("requestURI")
    public String  requestURL(HttpServletRequest request){
        return request.getRequestURI();
    }

    @ExceptionHandler({
            CommonException.class
    })
    public String handleErrorMessages(Model model, CommonException ex, HttpServletRequest request){
        for(ErrorMessage errorMessage : ex.getErrorMessageList()) {
            model.addAttribute(errorMessage.getFiledName(), errorMessage.getMessage());
        }
        model.addAttribute(ex.getObjectName(), ex.getObject());
        model.addAttribute("requestURI", request.getRequestURI());
        return ex.getReturnRoute();
    }
}
