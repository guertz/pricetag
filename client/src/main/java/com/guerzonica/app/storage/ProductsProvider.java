package com.guerzonica.app.storage;

import com.guerzonica.app.http.interfaces.RequestHandler;
import com.guerzonica.app.http.models.AmazonRequest;
import com.guerzonica.app.http.SocketRequest;
import com.guerzonica.app.http.interfaces.Body;
import com.guerzonica.app.http.HttpClient;
import com.guerzonica.app.http.Request;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.guerzonica.app.channel.Channel;
import com.guerzonica.app.channel.models.Packet;
import com.guerzonica.app.channel.interfaces.MessageHandler;
import com.guerzonica.app.channel.exceptions.StreamException;
import com.guerzonica.app.storage.models.*;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

public class ProductsProvider extends Storage {

    private static ProductsProvider provider = null;

    public static ProductsProvider getProvider() throws SQLException, StreamException, URISyntaxException {
        if(provider == null)
            provider = new ProductsProvider();

        return provider;
    }

    public final BehaviorSubject<Vector<ProductPrices>> collection$ = BehaviorSubject.create();

    private ResultSet listProducts() throws SQLException {
        Statement statement = Storage.getConnection().createStatement();

            return statement.executeQuery("SELECT * FROM " + Product.tableName);
    }

    public void addItem(ProductPrices item) {
        collection$
            .firstElement()
            .subscribe(new Consumer<Vector<ProductPrices>>() {

				@Override
				public void accept(Vector<ProductPrices> t) throws Exception {
                    t.add(item);
                    collection$.onNext(t);
				}
        
            });

    }

    public void fetchAmazonHttp(String asin, RequestHandler callback) throws MalformedURLException {
        AmazonRequest request = new AmazonRequest();
            request.setItedId(asin);
            request.setResponseGroup("Images,ItemAttributes,OfferFull");

        Request<String> httpClient = new HttpClient().makeClient(Body.class).request(request.getRequestUri());

            httpClient.start(callback);
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
        //
        // channel.bindRoute("broadcast:details", new MessageHandler() {
        //     @Override
        //     public void handle(String response) {
        //       System.out.println(response);
        //     }
        // });

        SocketRequest socket =  new HttpClient().makeClient(Body.class).bindRoute("broadcast:details");
        
        socket.start(new RequestHandler(){
            @Override
            public void handle(String data) {
              System.out.println("Data arrived");
            }
        });

        // javafx obervable where better?
        // possibility to push and listen only to changes
        Vector<ProductPrices> copy = new Vector<ProductPrices>();
        ResultSet productSet = this.listProducts();

        while(productSet.next()) {

            try {
                
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

                copy.add(listItem);
                
            } catch (Exception e) { e.printStackTrace(); }
        }

        this.collection$.onNext(copy);
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
