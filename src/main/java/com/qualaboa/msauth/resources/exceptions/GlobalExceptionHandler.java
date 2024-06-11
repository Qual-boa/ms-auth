package com.qualaboa.msauth.resources.exceptions;

import com.qualaboa.msauth.services.exceptions.ConflictException;
import com.qualaboa.msauth.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private PathInterceptor pathInterceptor;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        StandardError standardError = new StandardError();
        standardError.setError(e.getMessage());
        standardError.setPath(request.getRequestURI() + " (" + request.getMethod() + ")");
        standardError.setTimestamp(LocalDateTime.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<StandardError> resourceNotFound(ConflictException e, HttpServletRequest request){
        StandardError standardError = new StandardError();
        standardError.setError(e.getMessage());
        standardError.setPath(request.getRequestURI() + " (" + request.getMethod() + ")");
        standardError.setTimestamp(LocalDateTime.now());
        standardError.setStatus(HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(standardError);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQLException(SQLException ex) {
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<ErrorResponse> handleNotImplementedException(NotImplementedException ex) {
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_IMPLEMENTED, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN,ex.getMessage(),path,ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        System.out.println(ex.getClass().getTypeName());
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> badRequest(BadRequestException e, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setError(e.getMessage());
        standardError.setPath(request.getRequestURI() + " (" + request.getMethod() + ")");
        standardError.setTimestamp(LocalDateTime.now());
        standardError.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<StandardError> internalServerError(HttpServerErrorException.InternalServerError e, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setError(e.getMessage());
        standardError.setPath(request.getRequestURI() + " (" + request.getMethod() + ")");
        standardError.setTimestamp(LocalDateTime.now());
        standardError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardError);
    }
}