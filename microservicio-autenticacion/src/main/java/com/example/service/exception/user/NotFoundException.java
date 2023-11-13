package com.example.service.exception.user;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private EnumNotFound code = EnumNotFound.not_found;
    private final String message;

    public NotFoundException( String entity, Long id ){
        this.message = String.format( "%s con id %s no existe.", entity, id);
    }

    public NotFoundException( String entity, String id ){
        this.message = String.format( "%s con id %s no existe.", entity, id);
    }

    private enum EnumNotFound{
        not_found
    }

}
