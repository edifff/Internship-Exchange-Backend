package ru.rsreu.projectmanagment.identityservice.identityservice.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class APIExeption  extends RuntimeException{
    private final HttpStatus httpStatus;

    protected APIExeption(String message, HttpStatus status){
        super(message);
        httpStatus=status;
    }
}
