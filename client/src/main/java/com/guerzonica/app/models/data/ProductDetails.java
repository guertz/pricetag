package com.guerzonica.app.models.data;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.guerzonica.app.models.channel.Packet;

@XmlRootElement(name="product")
public class ProductDetails extends Product {

    @SerializedName(value="history")
    public Vector<History> history;

    public ProductDetails() {
        super();
        this.history = new Vector<History>();
    }

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

    @XmlElement
    public void setPrice(String price) {

        Date now = new Date();
        SimpleDateFormat stamp = 
            new SimpleDateFormat("dd/MM/yyyy");

        Number currency = Float.parseFloat(price) / 100;
        this.history.add(new History(stamp.format(now), currency));
    }
}