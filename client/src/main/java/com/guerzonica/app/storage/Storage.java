package com.guerzonica.app.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.guerzonica.app.env.Env;

import org.sqlite.SQLiteConfig;

public class Storage {

    private static Connection conn =  null;

    public static Connection getConnection() throws SQLException {
      if(conn == null) {
        SQLiteConfig config = new SQLiteConfig();
          config.enforceForeignKeys(true);

        conn = DriverManager.getConnection("jdbc:sqlite:" + Env.getPath(), config.toProperties());
      }
      return conn;
    }
  
}