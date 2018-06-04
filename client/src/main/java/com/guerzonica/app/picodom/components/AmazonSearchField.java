package com.guerzonica.app.picodom.components;

import com.guerzonica.app.http.interfaces.RequestHandler;
import com.guerzonica.app.http.models.AmazonResponse;
import com.guerzonica.app.picodom.components.SearchField;
import com.guerzonica.app.storage.ProductsProvider;
import com.guerzonica.app.storage.models.Offer;
import com.guerzonica.app.storage.models.Product;

import io.reactivex.functions.Consumer;

public class AmazonSearchField extends SearchField {

  public AmazonSearchField() {

    super("ASIN Prodotto");
    this.getActionButton().setOnAction(action -> {

      try {

        ProductsProvider provider = ProductsProvider.getProvider();

          provider.fetchAmazonHttp(getContent(), new RequestHandler () {

            @Override
            public void handle(String data) {
              try {
                Offer item = AmazonResponse.parse(data);

                provider.addProduct(item.getProduct(), true, false)
                  .subscribe(
                    new Consumer<Product>() {

                      @Override
                      public void accept(Product t) throws Exception {
                        provider.addPrice(item, true, true).subscribe();
                      }

                    },
                    new Consumer<Throwable>() {

                      @Override
                      public void accept(Throwable t) throws Exception {
                        // System.out.println("Got error");
                      }

                    }
                  );
              } catch (Exception e) { e.printStackTrace(); }
            }
      
          });
          
      } catch (Exception e) { e.printStackTrace(); }
    
    });

  }

}
