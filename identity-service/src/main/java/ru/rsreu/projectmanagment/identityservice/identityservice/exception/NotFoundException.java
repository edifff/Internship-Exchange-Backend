package ru.rsreu.projectmanagment.identityservice.identityservice.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends APIExeption{
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
