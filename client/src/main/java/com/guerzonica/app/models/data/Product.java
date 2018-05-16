package com.guerzonica.app.models.data;

import java.lang.reflect.Type;

import javax.xml.bind.annotation.XmlElement;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import com.guerzonica.app.models.channel.*;
import com.guerzonica.app.models.database.Database;

public class Product extends Database<String> implements Streammable {

    public static final String tableName = "products";

    @SerializedName(value="name")
    private String name;

    @SerializedName(value="image")
    private String image;

    @SerializedName(value="description")
    private String description;

    @SerializedName(value="link")
    private String link;

    public Product() {
        super();
    }

    public Product(String n, String d) {
        super();
        this.name = n;
        this.description = d;
    }

    public Product(String i, String n, String d) {
        super(i);
        this.name = n;
        this.description = d;
    }

    public Product(String i, String n, String d, String link) {
        super(i);
        this.name = n;
        this.description = d;
        this.link = link;
    }

    public static Type typeToken() {
        return new TypeToken<Packet<Product>>() {}.getType();
    }

    // validate on id length
    public static String schema() {
        return
            "CREATE TABLE if not exists " + tableName + "("
                + "id varchar(10),"
                + "name varchar(60),"
                + "description varchar(60),"
                + "link varchar(255),"
                + "primary key(id)"
          + ");";
    }

    public String delete()  {
      return super.delete(Product.tableName);
    }

    @XmlElement
    public void setAsin(String asin) {
        super.setId(asin);
    }

    // getId inherited from parent

    @XmlElement //too long?
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @XmlElement
    public void setImage(String image) {
        this.image = image;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
    
    @XmlElement
    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
      return this.link;
    }
}
