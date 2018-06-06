package com.guerzonica.app.channel.interfaces;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

import com.guerzonica.app.channel.models.Packet;

/**
 * Interface that indentifies a Packet Content
 * 
 * @author Matteo Guerzoni
 */
public interface Streammable {

    /**
     * Method that indentifies the parametrized Packet type used
     * in JSON serialization/deserialization.
     * 
     * @return The Java types that identifies the Packet with content.
     * 
     * @see com.guerzonica.app.channel.models.Packet#fromJson(String, Type) fromJson.
     * @see com.guerzonica.app.channel.models.Packet#toJson(Packet, Type) toJson.
     */
    public static Type typeToken() {
        return new TypeToken<Packet<Streammable>>() {}.getType();
    }
}