package org.fasttrackit.hotel.management.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ErrorResponse handleResourceNotFound(ResourceNotFoundException ex){
        return new ErrorResponse("RNF01", ex.getMessage());
    }
}

record ErrorResponse(String code, String message){

}
