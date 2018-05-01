package com.guerzonica.app.items;

import java.util.List;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.guerzonica.app.channel.packet.Packet;

public class ProductDetails extends Product{

    @SerializedName(value="history")
    public List<History> history;

    public ProductDetails(Product p) {
        super(p.getId(), p.getName(), p.getDescription());

        this.history = new ArrayList<History>();
    }

    public static Type typeToken() {
        return new TypeToken<Packet<ProductDetails>>() {}.getType();
    }

}