package com.guerzonica.app.storage.models;

import java.sql.SQLException;

import com.google.gson.annotations.SerializedName;

public abstract class Item<T> {

    @SerializedName(value="id")
    private T id;

    public Item() {
        this.id = null;
    }

    public Item(T i) {
        this.id = i;
    }

    protected void setId(T i) {
        this.id = i;
    }

    public T getId() {
        return this.id;
    }

    public Boolean isRecord() {
        return this.id != null;
    }

    public static void SCHEMA() throws SQLException {
        throw new SQLException("Undefined schema");
    }
    
    // CRUD Logic (Exceptions? External Statically?)
    // Provider should handle exceptions in various types
    // how to join & big queries collection on where?
    // how to safely remove quotes?
    // Java hibernate?
    // Check existing reference with joinable types (Offer)

    // Validate (data + id)
    // fetch current id after
    public abstract void CREATE() throws SQLException;
    public abstract void READ()   throws SQLException;
    public abstract void UPDATE() throws SQLException;
    public abstract void DELETE() throws SQLException;

}
