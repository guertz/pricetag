package com.guerzonica.app.storage;

import com.guerzonica.app.http.interfaces.RequestHandler;
import com.guerzonica.app.http.models.AmazonRequest;
import com.guerzonica.app.http.models.AmazonResponse;
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
import com.guerzonica.app.storage.exceptions.AlreadyExistException;
import com.guerzonica.app.storage.exceptions.NotFoundException;
import com.guerzonica.app.storage.models.*;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Data provider.
 * Singleton instance that provides the desidered data
 * 
 * @author Matteo Guerzoni
 */
public class ProductsProvider extends Storage {

  /** ProductsProvider instance */
  private static ProductsProvider provider = null;

  /** 
   * Create or return ProductsProvider instance
   * 
   * @return The ProductsProvider instance.
   * 
   * @throws SQLException If it fails to execute the query
   * @throws URISyntaxException If the Websocket URI fails to validate
   * @throws NotFoundException If the models are not found in the Database
   * @throws MalformedURLException If the Amazon Http request URL fails to parse
   */
  public static ProductsProvider getProvider() throws SQLException, URISyntaxException, NotFoundException, MalformedURLException {
    if(provider == null)
      provider = new ProductsProvider();

    return provider;
  }

  /** Websocket Channel to communicate with other peers */
  private Channel channel;

  /** BehaviorSubject containing the state of data */
  private final BehaviorSubject<Map<String, ProductPrices>> collection$ = BehaviorSubject.create();

  /** 
   * Obtain the products stored in the database
   * 
   * @return The products stored in the database as a ResultSet
   * @throws SQLException
   */
  private ResultSet listProducts() throws SQLException {
    Statement statement = Storage.getConnection().createStatement();

      return statement.executeQuery("SELECT * FROM " + Product.tableName);
  }

  /** 
   * Obtain the prices, stored in the database, releted to a product
   * 
   * @param p Reference to the Product
   * 
   * @return The prices stored in the database as a ResultSet
   * 
   * @throws SQLException
   */
  private ResultSet listProductOffers(Product p) throws SQLException {
    Statement statement = Storage.getConnection().createStatement();

      return statement.executeQuery(
        "SELECT * FROM " + Offer.tableName   + " " +
        "WHERE product = '" + p.getId()+ "'" + " " +
        "ORDER BY date ASC;"
      );
  }

  /** 
   * Obtain the state of data as an Observable
   * 
   * @return The state of data
   */
  public Observable<Map<String, ProductPrices>> getCollection() {
    return this.collection$;
  }

  /** 
   * Add a product to the dataset
   * 
   * @param item The Product to add in the dataset
   * @param write Flag that indicates if the changes has to be propagated to the database
   *
   * @return The inserted Product or an empty instance
   */
  public Observable<Product> addProduct(Product item, Boolean write) {
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

            Packet<Product> request = new Packet<Product>("details", item);
              channel.sendMessage(Packet.toJson(request, Product.typeToken()));

            return item;
          }
        }
      )
      .onErrorReturn(e -> new Product());
  }

  /** 
   * Delete a product in the dataset
   * 
   * @param item The Product to delete in the dataset
   * @param write Flag that indicates if the changes has to be propagated to the database
   *
   * @return The deleted Product or an empty instance
   */
  public Observable<Product> deleteProduct(Product item, Boolean write) {
    return this.getCollection()
      .take(1)
      .map(
        new Function<Map<String, ProductPrices>, Product>() {

          @Override
          public Product apply(Map<String, ProductPrices> map) throws Exception {

            if(!map.containsKey(item.getId()))
              throw new NotFoundException(item.getId());

            if(write) {

              for(Map.Entry<Long, Offer> price: map.get(item.getId()).prices.entrySet()) {
                  price.getValue().DELETE();
                }
              
              
              item.DELETE();
              
            }

            map.remove(item.getId());

            // if(notify)
              collection$.onNext(map);

            return item;
          }
        }
      )
      .onErrorReturn(e -> new Product());
  }

  /** 
   * Add a price to the dataset
   * 
   * @param item The Offer to add in the dataset
   * @param write Flag that indicates if the changes has to be propagated to the database
   *
   * @return The inserted Offer or an empty instance
   */
  public Observable<Offer> addPrice(Offer item, Boolean write) {
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
      )
      .onErrorReturn(e -> new Offer());
  }

  /** 
   * Find an element in the dataset
   * 
   * @param identifier The product identfier to look for
   * 
   * @return The ProductPrices or an empty instance
   */
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

      })
      .onErrorReturn(e -> new ProductPrices());
  }

  /** 
   * Prepare an Http Request to amazon webservice
   * 
   * @throws MalformedURLException If the Amazon Http request URL fails to parse
   * 
   * @param asin The product identfier to look for
   * @param callback The response handler
   */
  public void fetchAmazonHttp(String asin, RequestHandler callback) throws MalformedURLException {
    AmazonRequest request = new AmazonRequest();
      request.setItedId(asin);
      request.setResponseGroup("Images,ItemAttributes,OfferFull");

    @SuppressWarnings("all")
    Request<String> httpClient = new HttpClient().makeClient(Body.class).request(request.getRequestUri());

      httpClient.start(callback);
  }

  /** 
   * Create the ProductsProvider instance with initializations
   * 
   * @throws SQLException If it fails to execute the query
   * @throws URISyntaxException If the Websocket URI fails to validate
   * @throws NotFoundException If the models are not found in the Database
   * @throws MalformedURLException If the Amazon Http request URL fails to parse
   */
  ProductsProvider() throws SQLException, URISyntaxException, NotFoundException, MalformedURLException {
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

        findItem(element.getId())
          .subscribe(new Consumer<ProductPrices>() {

            @Override
            public void accept(ProductPrices compact) throws Exception {
              if(compact.getId() != null) {
                
                Packet<PriceMap> egress = new Packet<PriceMap>(
                    response.getUri(),
                    response.getRid(),
                    compact.prices
                  );

                channel.sendMessage(Packet.toJson(egress, PriceMap.typeToken()));
              }
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
          addPrice(entry.getValue(), true).subscribe();
        }

      }
    });

    ResultSet productSet = this.listProducts();

    while(productSet.next()) {

      Product item = new Product();
        item.READ(productSet);

      addProduct(item, false).subscribe();

      fetchAmazonHttp(item.getId(), new RequestHandler () {

        @Override
        public void handle(String data) {
          try {            
            addPrice(AmazonResponse.parse(data), true)
              .subscribe();

          } catch (Exception e) { e.printStackTrace(); }
        }
  
      });
      
      ResultSet offerSet = this.listProductOffers(item);

      while(offerSet.next()) {

        Offer offer = new Offer();
          offer.READ(offerSet);
          offer.setProduct(item);

        addPrice(offer, false).subscribe();

      }      

    }

  }

}
