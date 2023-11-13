package com.example.web.rest;

import com.example.service.exception.user.ErrorDTO;
import com.example.service.exception.user.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice( basePackages = "com.example.web.rest" )
public class GeneralExceptionHandler {

    @ExceptionHandler( NotFoundException.class )
    public ErrorDTO getException(NotFoundException ex ){
        return new ErrorDTO( ex.getCode(), ex.getMessage() );
    }
}
