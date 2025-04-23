package com.Job.Application.Exceptions;

import com.Job.Application.Exceptions.CustomExceptions.CompanyNotFoundException;
import com.Job.Application.Exceptions.CustomExceptions.FileNotUploaded;
import com.Job.Application.Exceptions.CustomExceptions.UnauthorizedAccess;
import com.Job.Application.Exceptions.CustomExceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            CompanyNotFoundException.class,
            FileNotUploaded.class,
            UnauthorizedAccess.class,
            UserNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleCustomExceptions(RuntimeException exception){

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if(exception instanceof UserNotFoundException || exception instanceof CompanyNotFoundException){
            status = HttpStatus.NOT_FOUND;
        } else if (exception instanceof  FileNotUploaded) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if(exception instanceof UnauthorizedAccess){
            status = HttpStatus.FORBIDDEN;
        }

        ErrorResponse errorResponse = new ErrorResponse(status.value(), exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception exception){

        ErrorResponse errorResponse = new ErrorResponse(500, "Unexpected error has occured", LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
