package com.guerzonica.app.models.database;

import com.google.gson.annotations.SerializedName;

// Generics on ID type
// generate automatically schema table
// to database model
// implement CRUD logic
// copy constructor
public class Database {

    @SerializedName(value="id")
    private Integer id;

    public Database() {
        this.id = null;
    }

    public Database(Integer i) {
        this.id = i;
    }

    public Integer getId() {
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
