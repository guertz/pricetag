package com.guerzonica.app.storage.models;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import com.guerzonica.app.channel.interfaces.Streammable;
import com.guerzonica.app.channel.models.Packet;
import com.guerzonica.app.storage.Storage;
import com.guerzonica.app.storage.exceptions.AlreadyExistException;
import com.guerzonica.app.storage.exceptions.NotFoundException;

@XmlRootElement
public class Product extends Item<String> implements Streammable {

    public static final String tableName = "products";

    @SerializedName(value="name")
    private String name;

    @SerializedName(value="image")
    private String image;

    @SerializedName(value="description")
    private String description;

    @SerializedName(value="link")
    private String link;

    @XmlElement
    public void setAsin(String asin) {
        super.setId(asin);
    }

    public String getName() {
        return this.name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return this.image;
    }

    @XmlElement
    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return this.description;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return this.link;
    }
    
    @XmlElement
    public void setLink(String link) {
        this.link = link;
    }

    public static Type typeToken() {
        return new TypeToken<Packet<Product>>() {}.getType();
    }

    public static void SCHEMA() throws SQLException {

        Statement statement = Storage.getConnection().createStatement();

            statement.execute(
                "CREATE TABLE if not exists " + tableName + "("
                    + "id varchar(10),"
                    + "name varchar(255),"
                    + "description varchar(255),"
                    + "link varchar(255),"
                    + "image varchar(255),"
                    + "primary key(id)"
                + ");"
            );
    }

    public void CREATE() throws AlreadyExistException, SQLException {
        PreparedStatement statement = Storage.getConnection()
            .prepareStatement("INSERT INTO " + tableName + " VALUES (?,?,?,?,?);");

            statement.setString(1, this.getId());
            statement.setString(2, this.name);
            statement.setString(3, this.description);
            statement.setString(4, this.link);
            statement.setString(5, this.image);

        statement.execute();
    }

    public void READ(ResultSet product) throws NotFoundException, SQLException {

        if(product.isClosed())
            throw new NotFoundException(this.getId());

        this.setAsin(product.getString("id"));

        this.name = product.getString("name");
        this.description = product.getString("description");
        this.link = product.getString("link");
        this.image = product.getString("image");

    }

    // better share code statically
    public void READ() throws NotFoundException, SQLException {
        Statement statement = Storage.getConnection().createStatement();
        ResultSet content   = statement.executeQuery("SELECT * FROM " + tableName + " where id = '" + this.getId() + "'");

            content.next();
        this.READ(content);
    }

    public void UPDATE() throws NotFoundException, SQLException {
        Statement statement = Storage.getConnection().createStatement();

            statement.execute(
                "UPDATE " + tableName + " SET " +
                    "name='" + this.name + "' " +
                    "description='" + this.description +"' " +
                    "link='" + this.link + "' " +
                    "image='" + this.image + "' " +
                    "WHERE id = '" + this.getId() + "'; "
            );
    }

    public void DELETE() throws SQLException {
        Statement statement  = Storage.getConnection().createStatement();

            statement.execute("DELETE FROM " + tableName + " WHERE id = '" + this.getId() + "'; ");
    }

}
