package com.guerzonica.app.storage.models;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.TreeMap;

import com.google.gson.reflect.TypeToken;
import com.guerzonica.app.channel.interfaces.Streammable;
import com.guerzonica.app.channel.models.Packet;

public class PriceMap extends TreeMap<Long, Offer> implements Streammable {

    private static final long serialVersionUID = 1L;

    public PriceMap() {

    }
    
    public PriceMap(Offer offer) throws ParseException {
        add(offer);
    }

    public Offer add(Offer value) throws ParseException {

        return super.put(value.getUnixDate(), value);
    }

    public static Type typeToken() {
        return new TypeToken<Packet<PriceMap>>() {}.getType();
    }

}