package com.guerzonica.app.channel.models;

import java.lang.reflect.Type;
import com.google.gson.annotations.SerializedName;
import com.guerzonica.app.channel.interfaces.Streammable;;

/**
 * Websocket message (with content). 
 * 
 * <br>
 * <ul>
 *  <li>
 *    Provides the minimal information required by a message
 *    to be sent and received on the Channel instance ({@link Container for details}).
 *  </li>
 *  <li>
 *    Allow to insert parametrized content inside the message. 
 *    Content can be any type that implements the Streammable interface
 *  </li>
 * </ul>
 * 
 * @author Matteo Guerzoni
 */
public class Packet<C extends Streammable> extends Container {

    /** 
     * Deserialize a JSON text file into a specific Packet
     * 
     * @param <C> The Packet content Type
     * @param blob The string to parse
     * @param contentType The Java Type that identifies the object
     * 
     * @return The parsed value as a parametrized Packet
     */
    public static <C extends Streammable> Packet<C> fromJson(String blob, Type contentType) {
        return _ser.fromJson(blob, contentType);
    }

    /** 
     * Serialize a specific Packet into a JSON text file.
     * 
     * @param <C> The Packet content Type
     * @param obj The packet to serialize
     * @param contentType The Java Type that identifies the object
     * 
     * @return The serialized string (JSON encoded)
     */
    public static <C extends Streammable> String toJson(Packet<C> obj, Type contentType) {
        return _ser.toJson(obj, contentType).toString();
    }

    /** The parametrized Packet content */
    @SerializedName(value="content")
    private C content;

    /**
     * Creates a Packet with identifier and specifying the content.
     * 
     * @param u The message URI
     * @param r The message identifier
     * @param c The message content
     * 
     * @see Container#Container(String, String)
     */
    public Packet(String u, String r, C c) {
        super(u, r);
        this.content = c;
    }

    /**
     * Creates a Packet specifying the content.
     * 
     * @param u The message URI
     * @param c The message content
     * 
     * @see Container#Container(String)
     */
    public Packet(String u, C c) {
        super(u);
        this.content = c;
    }

    public C getContent() {
        return this.content;
    }
}
