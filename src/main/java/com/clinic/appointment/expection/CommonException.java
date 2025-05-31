package com.clinic.appointment.expection;

import lombok.Data;

import java.util.List;

@Data
public class CommonException extends RuntimeException{
    private List<ErrorMessage> errorMessageList;
    private String returnRoute;
    private Object object;
    private String objectName;

    public CommonException(List<ErrorMessage> errorMessageList, String route, Object object, String objectName){
        this.errorMessageList = errorMessageList;
        this.returnRoute = route;
        this.object = object;
        this.objectName = objectName;
    }

}
