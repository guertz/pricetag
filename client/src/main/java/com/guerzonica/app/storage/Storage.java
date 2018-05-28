package com.guerzonica.app.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.guerzonica.app.env.Env;

public class Storage {

    private static Connection conn =  null;

    public static Connection getConnection() throws SQLException {
      if(conn == null) 
        conn = DriverManager.getConnection("jdbc:sqlite:" + Env.DataPath);

      return conn;
    }
  
}