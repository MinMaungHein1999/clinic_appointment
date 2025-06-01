package com.clinic.appointment.expection;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@ControllerAdvice
public class GloabalExceptionHandler {

    @ExceptionHandler({
            CommonException.class
    })
    public ModelAndView handleErrorMessages(CommonException ex, HttpServletRequest request){
        ModelAndView mav = new ModelAndView(ex.getReturnRoute());
        Model model = ex.getModel();

        if(model != null) {
            Map<String, Object> attrs = ex.getModel().asMap();
            for(Map.Entry<String, Object>  entry : attrs.entrySet()){
                mav.addObject(entry.getKey(), entry.getValue());
            }
        }

        for(ErrorMessage errorMessage : ex.getErrorMessageList()) {
            mav.addObject(errorMessage.getFiledName(), errorMessage.getMessage());
        }
        mav.addObject("requestURI", request.getRequestURI());
        return mav;
    }
}
