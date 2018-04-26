package com.guerzonica.app.items;

import com.google.gson.annotations.SerializedName;

public class Product  {

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
}