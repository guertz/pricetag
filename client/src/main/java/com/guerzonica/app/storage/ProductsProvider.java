package com.guerzonica.app.storage;

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.guerzonica.app.channel.Channel;
import com.guerzonica.app.channel.models.Packet;
import com.guerzonica.app.channel.interfaces.MessageHandler;
import com.guerzonica.app.channel.exceptions.StreamException;

import com.guerzonica.app.storage.models.*;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

public class ProductsProvider extends Storage {

    private static ProductsProvider provider = null;

    public static ProductsProvider getProvider() throws SQLException, StreamException, URISyntaxException {
        if(provider == null)
            provider = new ProductsProvider();

        return provider;
    }

    public final ObservableList<ProductPrices> collection = FXCollections.observableArrayList();

    private ResultSet listProducts() throws SQLException {
        Statement statement = Storage.getConnection().createStatement();

            return statement.executeQuery("SELECT * FROM " + Product.tableName);
    }

    private ResultSet listProductOffers(Product p) throws SQLException {
        Statement statement = Storage.getConnection().createStatement();

            return statement.executeQuery(
                    "SELECT * FROM " + Offer.tableName   + " " +
                    "WHERE product = '" + p.getId()+ "'" + " " +
                    "ORDER BY date ASC;"
                );
    }

    public ProductsProvider() throws SQLException, StreamException, URISyntaxException {
        Product.SCHEMA();
        Offer.SCHEMA();

        // Import fake data from AmazonHttp

        // Blocking (Either Future or Thread)
        // Channel channel = Channel.getChannel();

        // channel.bindRoute("broadcast:details", new MessageHandler() {
        //     @Override
        //     public void handle(String response) {
                
        //     }
        // });

        // Product unique key(product, date) [DB Constraint]
        collection.addListener(new ListChangeListener<ProductPrices>() {
			@Override
			public void onChanged(Change<? extends ProductPrices> item) {
                while(item.next()) {
                    Iterator<? extends ProductPrices> pp = item.getAddedSubList().iterator();
                    while(pp.hasNext()) {
                        final ProductPrices event$ = pp.next();
                        System.out.println("Was added: " + event$.product.getId());
                    }
                }
			}
        });

        ResultSet productSet = this.listProducts();

        while(productSet.next()) {
            ProductPrices listItem = new ProductPrices();
            Product product = new Product();
                product.READ(productSet);
                listItem.product = product;

            ResultSet offerSet = this.listProductOffers(product);

            while(offerSet.next()) {
                Offer offer = new Offer();
                    offer.READ(offerSet);
                    offer.setProduct(product);

                listItem.prices.add(offer);
            }

            // i can use object as a key?
            // no join product in offer type?
            // on write new item db (product/offer) should propagate updates
            collection.add(listItem);           
        }
    }

    // refetch on added on ObservableList
    public void fetchPeer(Product p) throws URISyntaxException, StreamException{
        Channel channel = Channel.getChannel();

            channel.bindRoute("details", new MessageHandler(){
                @Override
                public void handle(String response) {
                    
                }
            });

        // request with a specific product in database
        Packet<Product> request = new Packet<Product>("details", p);
            channel.sendMessage(Packet.toJson(request, Product.typeToken()));
    }

}
