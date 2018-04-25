package com.guerzonica.app.providers;

import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.guerzonica.app.transporter.StreamException;
import com.guerzonica.app.transporter.Transporter;

public class Prices extends DataAccessor {

    private static String tableName = "prices";

    private static Prices instance = null;

    public static Prices getProvider() throws SQLException, StreamException, URISyntaxException {
        if(instance == null)
            instance = new Prices();
        
        return instance;
    }

    private Prices() throws SQLException, StreamException, URISyntaxException {

        Products.getProvider();

        final Transporter broadCastr = Transporter.getTransporter();

        // method refresh or keep stream opened
        broadCastr.streamFromRequest("prices", new Transporter.MessageHandler() {
            public void handleMessage(String message) {
                System.out.println(message);
            }
        });

        // debug mode only (memory read)
        Statement tableCreator = this.getConnection().createStatement();
            
            tableCreator.execute("CREATE TABLE if not exists " + tableName + "(id integer,"
                + "date varchar(60),"
                + "price integer,"
                + "product integer,"
                + "primary key(id));"); // missing fk

        PreparedStatement prep = this.getConnection()
                .prepareStatement("INSERT INTO " + tableName + " VALUES(?,?,?,?);");

        // Data for motorola

            prep.setString(2, "22/04");
            prep.setInt(3, 200);
            prep.setInt(4, 1); // references product
        
        prep.execute();

            prep.setString(2, "23/04");
            prep.setInt(3, 179);
            prep.setInt(4, 1); // references product

        prep.execute();

            prep.setString(2, "25/04");
            prep.setInt(3, 205);
            prep.setInt(4, 1); // references product

        prep.execute();

            prep.setString(2, "26/04");
            prep.setInt(3, 200);
            prep.setInt(4, 1); // references product

        prep.execute();

            prep.setString(2, "29/04");
            prep.setInt(3, 193);
            prep.setInt(4, 1); // references product
            
        prep.execute();

        // Data for LG

            prep.setString(2, "22/04");
            prep.setInt(3, 155);
            prep.setInt(4, 2); // references product
        
        prep.execute();

            prep.setString(2, "23/04");
            prep.setInt(3, 160);
            prep.setInt(4, 2); // references product

        prep.execute();

            prep.setString(2, "25/04");
            prep.setInt(3, 162);
            prep.setInt(4, 2); // references product

        prep.execute();

            prep.setString(2, "26/04");
            prep.setInt(3, 161);
            prep.setInt(4, 2); // references product

        prep.execute();

    }

    public ResultSet getProductPrices(Number productId) throws SQLException {
        return this.queryResults(
            "SELECT " + tableName + ".* FROM " + tableName + " " +
            // "INNER JOIN products ON product = products.id"  +
            "WHERE product = " + productId);
    }
    
}