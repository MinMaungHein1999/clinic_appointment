package com.clinic.appointment.expection;

public class RestApiException extends Exception{
    public RestApiException(String e){
        super(e);
    }
}
