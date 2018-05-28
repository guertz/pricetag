package com.guerzonica.app.storage.exceptions;

public class NotFoundException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public NotFoundException(String identifier) {
        super(identifier);
    }

}