package ru.rsreu.projectmanagment.identityservice.identityservice.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends APIExeption{
    protected ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
