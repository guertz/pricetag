package com.guerzonica.app.storage.models;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import com.guerzonica.app.channel.interfaces.Streammable;
import com.guerzonica.app.channel.models.Packet;
import com.guerzonica.app.storage.Storage;

@XmlRootElement
public class Offer extends Item<Integer> implements Streammable {

    public static final String tableName = "offers";

    @SerializedName(value="date")
    private String date;

    @SerializedName(value="price")
    private Float price;

    private Product product;

    
    @XmlElement
    public void setValue(String offer) {

        Date now = new Date();
        SimpleDateFormat stamp = 
            new SimpleDateFormat("dd/MM/yyyy");

        Number currency = Float.parseFloat(offer) / 100;
        
        this.date  = stamp.format(now);
        this.price = currency.floatValue();
    }
    
    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Product getProduct() {
        return this.product;
    }

    @XmlElement
    public void setProduct(Product product) {
        this.product = product;
    }

    public static Type typeToken() {
        return new TypeToken<Packet<Offer>>() {}.getType();
    }

    public static void SCHEMA() throws SQLException {

        Statement statement = Storage.getConnection().createStatement();

            statement.execute(
                "CREATE TABLE if not exists " + tableName + "("
                    + "id integer,"
                    + "date varchar(60),"
                    + "price real,"
                    + "product varchar(10),"
                    + "primary key(id)"
                + ");"
            );
    }

    public void CREATE() throws SQLException {
        PreparedStatement statement = Storage.getConnection()
            .prepareStatement("INSERT INTO " + tableName + " VALUES (?,?,?,?);");

            statement.setString(2, this.date);
            statement.setFloat( 3, this.price);
            statement.setString(4, this.product.getId());

        statement.execute();
    }

    public void READ(ResultSet offer) throws SQLException {
        while(offer.next()) {
            Product product = new Product();
                product.setAsin(offer.getString("product"));

            this.price = offer.getFloat("price");
            this.date  = offer.getString("date");

            this.product = product;
        }
    }

    public void READ() throws SQLException {
        Statement statement = Storage.getConnection().createStatement();
        this.READ(statement.executeQuery("SELECT * FROM " + tableName + " where id = " + this.getId()));

        product.READ();
    }

    public void UPDATE() throws SQLException {
        throw new SQLException("Cannot update value");
    }

    public void DELETE() throws SQLException {
        Statement statement  = Storage.getConnection().createStatement();

            statement.execute("DELETE FROM " + tableName + " WHERE id = " + this.getId());
    }

}