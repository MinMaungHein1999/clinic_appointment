package com.clinic.appointment.util;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {
    public static int calculateAge(LocalDate birthDate){
        if(birthDate == null){
            throw  new IllegalArgumentException("Birth date cannot be null");
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
