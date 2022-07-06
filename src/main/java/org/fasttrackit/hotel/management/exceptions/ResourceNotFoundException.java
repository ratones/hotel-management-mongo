package org.fasttrackit.hotel.management.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String s) {
        super(s);
    }
}
