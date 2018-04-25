package com.guerzonica.app.providers;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DataAccessor {

    // garbace collector closes collection (if valid & exists)
    // access static variables with this.

    private static Connection conn =  null;

    protected ResultSet queryResults(String query) throws SQLException {
      Statement state = this.getConnection().createStatement();
      return state.executeQuery(query);
    }

    protected Connection getConnection() throws SQLException {
      if(conn == null) 
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");

      return conn;
    }
  
}