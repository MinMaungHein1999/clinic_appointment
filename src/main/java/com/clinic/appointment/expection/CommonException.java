package com.clinic.appointment.expection;

import lombok.Data;
import org.springframework.ui.Model;

import java.util.List;

@Data
public class CommonException extends RuntimeException{
    private List<ErrorMessage> errorMessageList;
    private String returnRoute;
    private Model model;

    public CommonException(List<ErrorMessage> errorMessageList, String route, Model model){
        this.errorMessageList = errorMessageList;
        this.returnRoute = route;
        this.model = model;
    }
}
