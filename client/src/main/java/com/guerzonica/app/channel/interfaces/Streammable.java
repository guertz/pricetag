package com.guerzonica.app.channel.interfaces;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

import com.guerzonica.app.channel.models.Packet;

public interface Streammable {
    public static Type typeToken() {
        return new TypeToken<Packet<Streammable>>() {}.getType();
    }
}