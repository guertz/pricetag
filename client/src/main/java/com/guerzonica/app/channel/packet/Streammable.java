package com.guerzonica.app.channel.packet;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

public class Streammable {
    public static Type typeToken() {
        return new TypeToken<Packet<Streammable>>() {}.getType();
    }
}