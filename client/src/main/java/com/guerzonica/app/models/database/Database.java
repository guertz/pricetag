package com.guerzonica.app.models.database;

import com.google.gson.annotations.SerializedName;

// generate automatically schema table
// to database model
// implement CRUD logic
// copy constructor
public class Database<T> {

    @SerializedName(value="id")
    private T id;

    public Database() {
        this.id = null;
    }

    public Database(T i) {
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

    public String delete(String tableName){
      return
        "DELETE FROM "+ tableName + " WHERE id = " + this.id;
    }

}
