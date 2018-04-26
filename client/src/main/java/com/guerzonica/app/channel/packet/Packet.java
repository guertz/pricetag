package com.guerzonica.app.channel.packet;

import java.lang.reflect.Type;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class Packet<C> extends Base {

    @SerializedName(value="content")
    private C content;
    
    public Packet(String u, C c) {
        super(u, UUID.randomUUID().toString());
        this.content = c;
    }

    public Packet(String u, String r, C c) {
        super(u, r);
        this.content = c;
    }

    public C getContent() {
        return this.content;
    }

    // make it common to all classes (internal member or extend base class)
    // alternative approch: Packets as OOP (extends/inherit) instead of generics
    private static Gson _ser = new Gson();

    private static <C> Type getType() {
        return (new TypeToken<Packet<C>>() {}).getType();
    }

    public static <C> Packet<C> fromStream(String blob) {
        return _ser.fromJson(blob, getType());
    }

    public static <C> String toStream(Packet<C> t) {
        return _ser.toJson(t);
    }
}