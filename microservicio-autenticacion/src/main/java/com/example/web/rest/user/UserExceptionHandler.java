package com.example.web.rest.user;

import com.example.service.exception.user.ErrorDTO;
import com.example.service.exception.user.UserException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice( basePackages = "com.monopatin.authservice.web.rest.user" )
public class UserExceptionHandler {

    @ExceptionHandler( UserException.class )
    public ErrorDTO getUserException(UserException ex ){
        return new ErrorDTO( ex.getCode(), ex.getMessage() );
    }
}
