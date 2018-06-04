package com.guerzonica.app.storage.models;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.TreeMap;

import com.google.gson.reflect.TypeToken;
import com.guerzonica.app.channel.interfaces.Streammable;
import com.guerzonica.app.channel.models.Packet;

/**
 * TreeMap used to store Offer in a sorted way.
 * 
 * <ul>
 *  <li>Unix format timestamp is used as a key</li>
 *  <li>Offer contains the price details (in a specific day)</li>
 * </ul>
 * 
 * The model implements the Streammable interface to be serialized
 * in a Packet format and then sended to other peers using a Channel
 * 
 * @author Matteo Guerzoni
 * 
 * @see com.guerzonica.app.channel.Channel Channel
 */
public class PriceMap extends TreeMap<Long, Offer> implements Streammable {

    private static final long serialVersionUID = 1L;

    public PriceMap() {

    }
    
    /** 
     * Create instance from Offer dataset 
     * 
     * @param offer The Offer to add
     * 
     * @throws ParseException If the date is not in a valid format
     * */
    public PriceMap(Offer offer) throws ParseException {
        add(offer);
    }

    /**
     * Add an Offer to the map taking care of key parsing
     * in the right format.
     * 
     * @param value The Offer to add
     * 
     * @return The added element
     * 
     * @throws ParseException If the date is not in a valid format
     */
    public Offer add(Offer value) throws ParseException {

        return super.put(value.getUnixDate(), value);
    }

    public static Type typeToken() {
        return new TypeToken<Packet<PriceMap>>() {}.getType();
    }

}