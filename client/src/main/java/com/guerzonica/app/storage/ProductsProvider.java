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
import java.util.HashMap;
import java.util.Map;

import com.guerzonica.app.channel.Channel;
import com.guerzonica.app.channel.models.Packet;
import com.guerzonica.app.channel.interfaces.MessageHandler;
import com.guerzonica.app.channel.exceptions.StreamException;
import com.guerzonica.app.storage.models.*;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;

public class ProductsProvider extends Storage {

    private static ProductsProvider provider = null;

    public static ProductsProvider getProvider() throws SQLException, StreamException, URISyntaxException {
        if(provider == null)
            provider = new ProductsProvider();

        return provider;
    }

    private final BehaviorSubject<Map<String, ProductPrices>> collection$ = BehaviorSubject.create();

    private ResultSet listProducts() throws SQLException {
        Statement statement = Storage.getConnection().createStatement();

            return statement.executeQuery("SELECT * FROM " + Product.tableName);
    }

    public Observable<Map<String, ProductPrices>> getCollection() {
        return this.collection$;
    }

    public void addOrUpdate(Offer newEl) {
        this.getCollection()
            .take(1)
            .subscribe(
                new Consumer<Map<String, ProductPrices>>() {

					@Override
					public void accept(Map<String, ProductPrices> m) throws Exception {
                        
                        if(!m.containsKey(newEl.getProduct().getId())) {
                            newEl.getProduct().CREATE();
                            newEl.CREATE();
                            m.put(newEl.getProduct().getId(), new ProductPrices(newEl));
                        } else {
                            newEl.CREATE();
                            m.get(newEl.getProduct().getId()).add(newEl);
                        }

                        collection$.onNext(m);
					}

                }
            );
    }

    public Observable<ProductPrices> findItem(String identifier) {

        return this.getCollection()
            .map(new Function<Map<String, ProductPrices>, ProductPrices>() {

				@Override
				public ProductPrices apply(Map<String, ProductPrices> t) throws Exception {
					return t.get(identifier);
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

        Map<String, ProductPrices> copy = new HashMap<>();
        ResultSet productSet = this.listProducts();

        while(productSet.next()) {

            try {
                
                ProductPrices item = new ProductPrices(
                    new Product() {{ READ(productSet); }});

                // CRUD for ProductPrices
                ResultSet offerSet = this.listProductOffers(item.getProduct());

                while(offerSet.next()) {

                    // in read pass both productSet, offerSet
                    Offer offer = new Offer();
                        offer.READ(offerSet);
                        offer.setProduct(item.getProduct());

                    item.add(offer);
                }

                copy.put(item.getProduct().getId(), item);
                
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
