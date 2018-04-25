package com.guerzonica.app.transporter;

public class StreamException extends Exception {
    
    private static final long serialVersionUID = 5018731411110516103L;

	public StreamException(String streamKey) {
        super("Stream for " + streamKey + " already exists.");
    }
}