package com.clinic.appointment.expection;

import lombok.Data;

@Data
public class ErrorMessage {
    private String filedName;
    private String message;
}
