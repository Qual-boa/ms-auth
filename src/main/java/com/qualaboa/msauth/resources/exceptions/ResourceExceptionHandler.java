package com.qualaboa.msauth.resources.exceptions;

import com.qualaboa.msauth.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        StandardError standardError = new StandardError();
        standardError.setError(e.getMessage());
        standardError.setPath(request.getRequestURI() + " (" + request.getMethod() + ")");
        standardError.setTimestamp(LocalDateTime.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }
}
