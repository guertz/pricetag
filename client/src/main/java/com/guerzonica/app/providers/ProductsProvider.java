package com.guerzonica.app.providers;

import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.guerzonica.app.channel.Channel;
import com.guerzonica.app.channel.StreamException;
import com.guerzonica.app.mocks.SampleProducts;
import com.guerzonica.app.models.channel.*;
import com.guerzonica.app.models.data.*;

class BroadcastDetailsHandler implements IHandler {

    // Seek in local data (Read + Send)
    public void handleMessage(String blob) {

        System.out.println(blob);
        // Packet<Product> response = Packet.fromJson(blob, Product.typeToken());

        // ProductDetails pp = new ProductDetails(response.getContent());
        //     pp.history.add(new History("22/04", 100));
        //     pp.history.add(new History("23/04",  50));
        //     pp.history.add(new History("25/04", 120));
            
        // Packet<ProductDetails> request = new Packet<ProductDetails>(response.getUri(), response.getRid(), pp);
        //     Channel.getChannel().sendMessage(Packet.toJson(request, ProductDetails.typeToken()));
            
    }
}

class DetailsHandler implements IHandler {

    // Merge data on client (Receive + Write)
    public void handleMessage(String blob) {
        System.out.println(blob);
    }
}

public class ProductsProvider extends DataAccessor {

    private static ProductsProvider provider = null;

    public static ProductsProvider getProvider() throws SQLException {
        if(provider == null)
            provider = new ProductsProvider();
        
        return provider;
    }

    public ProductsProvider() throws SQLException {

        Statement schema = this.getConnection().createStatement();
            schema.execute(Product.schema());
            schema.execute(History.schema());

        // debugging purposes
        SampleProducts.make().forEach(product -> {
            try {
                this.writeForce(product);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // register only one => constructor code only
    public void askBroadcastHistory(Product p) throws URISyntaxException, StreamException{
        final Channel channel = Channel.getChannel();
        
            channel.bindRoute("broadcast:details", new BroadcastDetailsHandler());
            channel.bindRoute("details", new DetailsHandler());

        // request with a specific product in database
        Packet<Product> request = new Packet<Product>("details", p);
            channel.sendMessage(Packet.toJson(request, Product.typeToken())); 
    }

    public ProductDetails getByAsin(Integer asin) throws SQLException {

        Vector<ProductDetails> products = this.getAll("WHERE id = " + asin);

        if(products.size() == 0)
            throw new SQLException("The desidered data was not found");

        return products.firstElement();
    }

    public Vector<ProductDetails> getAll() throws SQLException {
        return this.getAll("");
    }

    private void writeForce(ProductDetails p) throws SQLException {
        PreparedStatement _p_product = this.getConnection()
                .prepareStatement("INSERT INTO " + ProductDetails.tableName + " VALUES (?,?,?);");

            _p_product.setInt(   1, p.getId());
            _p_product.setString(2, p.getName());
            _p_product.setString(3, p.getDescription());

        _p_product.execute();

        p.history.forEach(h -> {
            try {
                PreparedStatement _p_history = this.getConnection()
                        .prepareStatement("INSERT INTO " + History.tableName + " VALUES (?,?,?,?);");

                    _p_history.setString(2, h.getDate());
                    _p_history.setFloat( 3, h.getPrice());
                    _p_history.setInt(   4, p.getId());

                _p_history.execute();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private Vector<ProductDetails> getAll(String where) throws SQLException {
        Vector<ProductDetails> products = new Vector<ProductDetails>();

        ResultSet _r_products = this.queryResults(
            "SELECT " + Product.tableName + ".* FROM " + Product.tableName + " " + where);

        while(_r_products.next()) {
            ProductDetails product = new ProductDetails(
                new Product(
                    _r_products.getInt("id"),
                    _r_products.getString("name"), 
                    _r_products.getString("description")
                )
            );
    
            ResultSet _r_prices = this.queryResults(
                "SELECT " + History.tableName + ".* FROM " + History.tableName + " " +
                "WHERE product = " + product.getId() + " " +
                "ORDER BY date");

            while(_r_prices.next()) {
                product.history.add(
                    new History(
                        _r_prices.getInt("id"),
                        _r_prices.getString("date"), 
                        _r_prices.getFloat("price")
                    )
                );
            }

            products.add(product);            
        }

        return products;
    }

}