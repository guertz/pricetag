package com.guerzonica.app.storage;

import com.guerzonica.app.http.interfaces.RequestHandler;
import com.guerzonica.app.http.models.AmazonRequest;
import com.guerzonica.app.http.SocketRequest;
import com.guerzonica.app.http.interfaces.Body;
import com.guerzonica.app.http.HttpClient;
import com.guerzonica.app.http.Request;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.guerzonica.app.channel.Channel;
import com.guerzonica.app.channel.models.Packet;
import com.guerzonica.app.storage.exceptions.AlreadyExistException;
import com.guerzonica.app.storage.exceptions.NotFoundException;
import com.guerzonica.app.storage.models.*;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;

public class ProductsProvider extends Storage {

  private static ProductsProvider provider = null;

  public static ProductsProvider getProvider() throws Exception {
    if(provider == null)
      provider = new ProductsProvider();

    return provider;
  }

  private Channel channel;

  private final BehaviorSubject<Map<String, ProductPrices>> collection$ = BehaviorSubject.create();

  private ResultSet listProducts() throws SQLException {
    Statement statement = Storage.getConnection().createStatement();

      return statement.executeQuery("SELECT * FROM " + Product.tableName);
  }

  public Observable<Map<String, ProductPrices>> getCollection() {
    return this.collection$;
  }

  //TODO: remember future/subscribe
  //TODO: local map copy, if i dont notify i loose data
  //TODO: bind observable/fetch
  //TODO: read item?
  //TODO: throw runtime exception (ex. threads, provider)

  public Observable<Product> addProduct(Product item, Boolean write, Boolean notify) {
    return this.getCollection()
      .take(1)
      .map(
        new Function<Map<String, ProductPrices>, Product>() {

          @Override
          public Product apply(Map<String, ProductPrices> map) throws Exception {

            if(map.containsKey(item.getId()))
              throw new AlreadyExistException(item.getId());

            if(write)
              item.CREATE();

            map.put(item.getId(), new ProductPrices(item));

            // if(notify)
              collection$.onNext(map);

            // fetching other peers
            Packet<Product> request = new Packet<Product>("details", item);
              channel.sendMessage(Packet.toJson(request, Product.typeToken()));

            return item;
          }
        }
      );
  }

  //TODO: public Observable<Product> updateProduct(Product item, Boolean write, Boolean notify)
  
  public Observable<Product> deleteProduct(Product item, Boolean write, Boolean notify) {
    return this.getCollection()
      .take(1)
      .map(
        new Function<Map<String, ProductPrices>, Product>() {

          @Override
          public Product apply(Map<String, ProductPrices> map) throws Exception {

            if(!map.containsKey(item.getId()))
              throw new NotFoundException(item.getId());

            if(write)
              item.DELETE();

            map.remove(item.getId());

            // if(notify)
              collection$.onNext(map);

            return item;
          }
        }
      );
  }

  public Observable<Offer> addPrice(Offer item, Boolean write, Boolean notify) {
    return this.getCollection()
      .take(1)
      .map(
        new Function<Map<String, ProductPrices>, Offer>() {

          @Override
          public Offer apply(Map<String, ProductPrices> map) throws Exception {
            String parentRef = item.getProduct().getId();

            if(!map.containsKey(parentRef))
              throw new NotFoundException(parentRef);

            if(write)
              item.CREATE();

            map.get(parentRef).prices.add(item);

            // if(notify)
              collection$.onNext(map);

            return item;
          }
        }
      );
  }

  //TODO: public Observable<Offer> updatePrice(Offer item, Boolean write, Boolean notify)
  //TODO: public Observable<Offer> deletePrice(Offer item, Boolean write, Boolean notify)

  public Observable<ProductPrices> findItem(String identifier) {
    return this.getCollection()
      .take(1)
      .map(new Function<Map<String, ProductPrices>, ProductPrices>() {

				@Override
				public ProductPrices apply(Map<String, ProductPrices> map) throws Exception {
          if(!map.containsKey(identifier))
            throw new NotFoundException(identifier);

          return map.get(identifier);
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

  public ProductsProvider() throws Exception {
    Product.SCHEMA();
    Offer.SCHEMA();

    this.channel = Channel.getChannel();
    
    collection$.onNext(new HashMap<>());

    SocketRequest broadcastReceiver = new HttpClient()
      .makeClient(Body.class)
      .bindRoute("broadcast:details");
        
    SocketRequest detailsReceiver = new HttpClient()
      .makeClient(Body.class)
      .bindRoute("details");

    broadcastReceiver.start(new RequestHandler() {
      @Override
      public void handle(String data) {
        Packet<Product> response = Packet.fromJson(data, Product.typeToken());
        Product         element  = response.getContent();

        // TODO: dispose onErrorConsumer
        // TODO: onDelete cascade
        findItem(element.getId())
          .subscribe(new Consumer<ProductPrices>() {

            @Override
            public void accept(ProductPrices compact) throws Exception {
              Packet<PriceMap> egress = new Packet<PriceMap>(
                  response.getUri(),
                  response.getRid(),
                  compact.prices
                );

              channel.sendMessage(Packet.toJson(egress, PriceMap.typeToken()));
            }

          });
      }
    });

    detailsReceiver.start(new RequestHandler() {
      @Override
      public void handle(String data) {
        Packet<PriceMap> response = Packet.fromJson(data, PriceMap.typeToken());
        PriceMap         prices  = response.getContent();

        for(Map.Entry<Long, Offer> entry : prices.entrySet()) {
          System.out.println("Got prices: " + entry.getValue().getPrice());
          // addPrice(entry.getValue(), true, true).subscribe();
        }

      }
    });

    ResultSet productSet = this.listProducts();

    while(productSet.next()) {

      Product item = new Product();
        item.READ(productSet);

      addProduct(item, false, false).subscribe();

      ResultSet offerSet = this.listProductOffers(item);

      while(offerSet.next()) {

        Offer offer = new Offer();
          offer.READ(offerSet);
          offer.setProduct(item);

        addPrice(offer, false, false).subscribe();

      }      

    }

  }

}
