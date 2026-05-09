package ru.rsreu.projectmanagment.identityservice.identityservice.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends APIExeption{

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
