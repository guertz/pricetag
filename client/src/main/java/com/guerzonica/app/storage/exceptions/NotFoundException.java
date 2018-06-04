package com.guerzonica.app.storage.exceptions;

/**
 * Exception thrown when in CRUD operation the request item
 * is not found after performing the SQL Query.
 * 
 * @author Matteo Guerzoni
 * 
 * @see com.guerzonica.app.storage.models.Item
 */
public class NotFoundException extends Exception {

    private static final long serialVersionUID = 1L;
    
    /**
     * Create an Exception instance
     * 
     * @param identifier The element identifier
     */
    public NotFoundException(String identifier) {
        super(identifier);
    }

}