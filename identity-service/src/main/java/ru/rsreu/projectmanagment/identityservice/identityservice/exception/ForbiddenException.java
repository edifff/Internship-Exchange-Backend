package ru.rsreu.projectmanagment.identityservice.identityservice.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends APIExeption{
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
