package com.guerzonica.app.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Storage {

    private static Connection conn =  null;

    public static Connection getConnection() throws SQLException {
      if(conn == null) 
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");

      return conn;
    }
  
}