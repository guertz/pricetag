package com.guerzonica.app.models.data;

import java.lang.reflect.Type;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import com.guerzonica.app.models.channel.*;
import com.guerzonica.app.models.database.Database;

public class Product extends Database implements Streammable {

    public static final String tableName = "products";
    
    @SerializedName(value="name")
    private String name;

    @SerializedName(value="description")
    private String description;

    public Product(String n, String d) {
        super();
        this.name = n;
        this.description = d;
    }

    public Product(Integer i, String n, String d) {
        super(i);
        this.name = n;
        this.description = d;
    }

    public static Type typeToken() {
        return new TypeToken<Packet<Product>>() {}.getType();
    }

    public static String schema() {
        return 
            "CREATE TABLE if not exists " + tableName + "("
                + "id integer,"
                + "name varchar(60),"
                + "description varchar(60),"
                + "primary key(id)"
          + ");";
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}