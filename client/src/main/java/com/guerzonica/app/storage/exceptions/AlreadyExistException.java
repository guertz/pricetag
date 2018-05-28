package com.guerzonica.app.storage.exceptions;

public class AlreadyExistException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public AlreadyExistException(String asin) {
        super(asin);
    }

}