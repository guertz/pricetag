package com.guerzonica.app.providers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Products extends DataAccessor {

    private static String tableName = "products";

    private static Products instance = null;

    public static Products getProvider() throws SQLException {
        if(instance == null)
            instance = new Products();
        
        return instance;
    }

    private Products() throws SQLException {

        // debug mode only (memory read)
        Statement tableCreator = this.getConnection().createStatement();
            
            tableCreator.execute("CREATE TABLE if not exists " + tableName + "(id integer,"
                + "name varchar(60),"
                + "description varchar(60),"
                + "primary key(id));");

        PreparedStatement prep = this.getConnection()
                .prepareStatement("INSERT INTO " + tableName + " VALUES(?,?,?);");

            prep.setInt(1, 1);
            prep.setString(2, "Motorola G4 P");
            prep.setString(3, "Telefono di ultima generazione...");

        prep.execute();

            prep.setInt(1, 2);
            prep.setString(2, "LG G6");
            prep.setString(3, "Il nuovo modello prodotto da LG...");
            
        prep.execute();
        
    }

    public ResultSet getAll() throws SQLException {
        return this.queryResults("SELECT " + tableName + ".* FROM " + tableName);
    }

}