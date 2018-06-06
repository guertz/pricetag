package com.guerzonica.app.channel.models;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Websocket message container. 
 * It provides the minimal information required by a message 
 * to be sent and received on the Channel instance
 * 
 * @author Matteo Guerzoni
 */
public class Container {

    /** The JSON serializer/deserializer */
    protected static Gson _ser = new Gson();

    /** 
     * Deserialize a JSON text file into a Container.
     * 
     * @param blob The string to parse
     * @return The parsed value as a Container
     */
    public static Container fromJson(String blob) {
        return _ser.fromJson(blob, Container.class);
    }

    /** 
     * Serialize a Container into a JSON text file.
     * 
     * @param c The element to serialize
     * @return The serialized string (JSON encoded)
     */
    public static String toJson(Container c) {
        return _ser.toJson(c, Container.class);
    }

    /** The message URI (route) */
    @SerializedName(value="uri")
    private String uri;

    /** The unique message identifier */
    @SerializedName(value="rid")
    private String rid;

    /**
     * Create a Container with identifier.
     * 
     * @param u The message URI
     * @param r The message identifier
     */
    public Container(String u, String r) {
        this.uri = u;
        this.rid = r;
    }

    /**
     * Create a Container.
     * Generates an unique message identifier (UUID).
     * 
     * @param u The message URI
     */
    public Container(String u) {
        this(u, UUID.randomUUID().toString());
    }

    public String getRid() {
        return this.rid;
    }

    public String getUri() {
        return this.uri;
    }

}
