package com.guerzonica.app.storage.exceptions;

/**
 * Exception thrown when in CRUD operation the request item
 * already exist after performing the SQL Insert Query.
 * 
 * @author Matteo Guerzoni
 * 
 * @see com.guerzonica.app.storage.models.Item
 */
public class AlreadyExistException extends Exception {

    private static final long serialVersionUID = 1L;
    
    /**
     * Create an Exception instance
     * 
     * @param identifier The element identifier
     */
    public AlreadyExistException(String identifier) {
        super(identifier);
    }

}