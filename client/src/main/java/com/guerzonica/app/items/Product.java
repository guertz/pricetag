package com.guerzonica.app.items;

import java.lang.reflect.Type;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.guerzonica.app.channel.packet.*;

public class Product extends Streammable {

    @SerializedName(value="id")
    private Number id;

    @SerializedName(value="name")
    private String name;

    @SerializedName(value="description")
    private String description;

    public Product(Number id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static Type typeToken() {
        return new TypeToken<Packet<Product>>() {}.getType();
    }

    public Number getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}