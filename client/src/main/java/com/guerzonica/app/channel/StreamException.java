package com.guerzonica.app.channel;

public class StreamException extends Exception {
    
    private static final long serialVersionUID = 5018731411110516103L;

	public StreamException(String bindKey) {
        super("Binding for " + bindKey + " already exists.");
    }
}