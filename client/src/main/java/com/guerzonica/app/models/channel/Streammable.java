package com.guerzonica.app.models.channel;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

public interface Streammable {
    public static Type typeToken() {
        return new TypeToken<Packet<Streammable>>() {}.getType();
    }
}