package com.clinic.appointment.expection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException badCredentialsException){
        Map<String, Object> response= new HashMap<>();
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("error", "Unauthorized");
        response.put("message", "Invalid username or password");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<?> handleUnavailableApi(Exception e){
        Map<String, Object> response= new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "invalid_url");
        response.put("message", "Invalid URL Found");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
