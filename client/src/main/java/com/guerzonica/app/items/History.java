package com.guerzonica.app.items;

import com.google.gson.annotations.SerializedName;

public class History {

    private Integer id;

    @SerializedName(value="date")
    private String date;

    @SerializedName(value="price")
    private Integer price;

    public History(String d, Integer i) {
        this.date = d;
        this.price = i;
    }

    public String getDate() {
        return this.date;
    }

}