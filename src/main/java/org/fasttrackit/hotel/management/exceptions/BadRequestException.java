package org.fasttrackit.hotel.management.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String s) {
        super(s);
    }
}
