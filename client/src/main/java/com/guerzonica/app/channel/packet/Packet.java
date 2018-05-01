package com.guerzonica.app.channel.packet;

import java.lang.reflect.Type;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Packet<C extends Streammable> {

    private static Gson _ser = new Gson();

    public static <C extends Streammable> Packet<C> fromJson(String blob, Type contentType) {
        return _ser.fromJson(blob, contentType);
    }

    public static <C extends Streammable> String toJson(Packet<C> c, Type contentType) {
        return _ser.toJson(c, contentType).toString();
    }

    @SerializedName(value="uri")
    private String uri;

    @SerializedName(value="rid")
    private String rid; // request identifier

    @SerializedName(value="content")
    private C content;
    
    public Packet(String u, String r, C c) {
        this.uri = u;
        this.rid = r;
        this.content = c;
    }

    public Packet(String u, C c) {
        this(u, UUID.randomUUID().toString(), c);
    }

    public String getRid() {
        return this.rid;
    }

    public String getUri() {
        return this.uri;
    }

    public C getContent() {
        return this.content;
    }
}