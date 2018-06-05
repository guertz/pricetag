package com.guerzonica.app.channel.exceptions;

/**
 * Exception thrown when a route handler binding already exists on the instance.
 * 
 * @author Matteo Guerzoni
 * 
 * @see com.guerzonica.app.channel.Channel#bindRoute(String, com.guerzonica.app.channel.interfaces.MessageHandler)
 */
public class StreamException extends Exception {
    
    private static final long serialVersionUID = 5018731411110516103L;

    /**
     * Create an Exception instance
     * 
     * @param bindKey The binding identifier (route)
     */
	public StreamException(String bindKey) {
        super("Binding for " + bindKey + " already exists.");
    }
}