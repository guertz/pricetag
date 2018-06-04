package com.guerzonica.app.channel.models;

import java.lang.reflect.Type;

import com.google.gson.annotations.SerializedName;

import com.guerzonica.app.channel.interfaces.Streammable;;

public class Packet<C extends Streammable> extends Container {

    public static <C extends Streammable> Packet<C> fromJson(String blob, Type contentType) {
        return _ser.fromJson(blob, contentType);
    }

    public static <C extends Streammable> String toJson(Packet<C> c, Type contentType) {
        return _ser.toJson(c, contentType).toString();
    }

    @SerializedName(value="content")
    private C content;

    public Packet(String u, String r, C c) {
        super(u, r);
        this.content = c;
    }

    public Packet(String u, C c) {
        super(u);
        this.content = c;
    }

    public C getContent() {
        return this.content;
    }
}
