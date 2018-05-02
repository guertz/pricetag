package com.guerzonica.app.models.data;

import com.google.gson.annotations.SerializedName;
import com.guerzonica.app.models.database.Database;

public class History extends Database {

    public static final String tableName = "histories";

    @SerializedName(value="date")
    private String date;

    @SerializedName(value="price")
    private Float price;

    public History(String d, Number p) {
        super();
        this.date = d;
        this.price = p.floatValue();
    }

    public History(Integer i, String d, Number p) {
        super(i);
        this.date = d;
        this.price = p.floatValue();
    }

    public static String schema() {
        return 
            "CREATE TABLE if not exists " + tableName + "("
                + "id integer,"
                + "date varchar(60),"
                + "price real,"
                + "product integer,"
                + "primary key(id)"
          + ");";
    }

    public String getDate() {
        return this.date;
    }

    public Float getPrice() {
        return this.price;
    }

}