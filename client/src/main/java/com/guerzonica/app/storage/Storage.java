package com.guerzonica.app.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.guerzonica.app.env.Env;

import org.sqlite.SQLiteConfig;

/**
 * Database connection. 
 * Singleton instance that handles the connection to database
 * 
 * @author Matteo Guerzoni
 */
public class Storage {

    /** Connection instance */
    private static Connection conn =  null;

    /** 
     * Create or Return Connection instance
     * 
     * @return The Connection instance.
     * 
     * @throws SQLException If the connection to the Database fails
     */
    public static Connection getConnection() throws SQLException {
      if(conn == null) {
        SQLiteConfig config = new SQLiteConfig();
          config.enforceForeignKeys(true);

        conn = DriverManager.getConnection("jdbc:sqlite:" + Env.getPath(), config.toProperties());
      }
      return conn;
    }
  
}