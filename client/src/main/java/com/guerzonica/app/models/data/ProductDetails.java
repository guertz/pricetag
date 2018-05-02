package com.guerzonica.app.models.data;

import java.lang.reflect.Type;
import java.util.Vector;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.guerzonica.app.models.channel.Packet;

public class ProductDetails extends Product {

    @SerializedName(value="history")
    public Vector<History> history;

    public ProductDetails(Product p) {
        super(p.getId(), p.getName(), p.getDescription());
        this.history = new Vector<History>();
    }

    public ProductDetails(Product p, Vector<History> h) {
        super(p.getId(), p.getName(), p.getDescription());
        this.history = h;
    }

    // @Override
    public static Type typeToken() {
        return new TypeToken<Packet<ProductDetails>>() {}.getType();
    }

}