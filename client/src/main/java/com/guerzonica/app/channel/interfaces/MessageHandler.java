package com.guerzonica.app.channel.interfaces;

/**
 * Interface that indentifies a MessageHandler callback
 * 
 * @author Matteo Guerzoni
 */
public interface MessageHandler {

    /**
     * Method that will be invoked when a message that match
     * the specified binding is received.
     * 
     * @param response The message value (JSON encoded)
     * 
     * @see com.guerzonica.app.channel.Channel#bindRoute(String, MessageHandler)
     */
    public void handle(String response);
}