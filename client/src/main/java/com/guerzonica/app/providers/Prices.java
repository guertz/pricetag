package com.guerzonica.app.providers;

import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.guerzonica.app.channel.handler.*;
import com.guerzonica.app.channel.packet.*;
import com.guerzonica.app.channel.*;
import com.guerzonica.app.items.Product;

// use proper generics classes (add support for ProductPrice in time)
// handle data coming from aws
// use classes to display data in graphs
// Integer instead of number
class BroadcastDetailsHandler implements IHandler {

    public void handleMessage(String blob) {
        Packet<Product> response = Packet.fromStream(blob);
        // read the data i have about that product
        // collect it in periods (or days)
        // send it back to the server with method in provider
    }
}

class DetailsHandler implements IHandler {

    public void handleMessage(String blob) {
        Packet<Product> response = Packet.fromStream(blob);
        // read the data i recive from the server
        // merge with mine
        // update view (observable?)
    }
}

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

        final Channel broadCastr = Channel.getChannel();
        
        broadCastr.bindRoute("broadcast:details", new BroadcastDetailsHandler());
        broadCastr.bindRoute("details", new DetailsHandler());

        // request with a specific product in database
        Packet<Product> request = new Packet<Product>("details", new Product(1, "Motorola", "Moto G4 P"));
            broadCastr.sendMessage(Packet.toStream(request));        

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