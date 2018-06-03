package com.guerzonica.app.picodom.components;

import com.guerzonica.app.http.interfaces.RequestHandler;
import com.guerzonica.app.http.models.AmazonResponse;
import com.guerzonica.app.picodom.components.SearchField;
import com.guerzonica.app.storage.ProductsProvider;


public class AmazonSearchField extends SearchField {

  public AmazonSearchField() {

    super("ASIN Prodotto");
    this.getActionButton().setOnAction(action -> {

      try {

        ProductsProvider provider = ProductsProvider.getProvider();

          provider.fetchAmazonHttp(getContent(), new RequestHandler () {

            // new item push handler
            // add error handler (!= 200)
            @Override
            public void handle(String data) {
              try {
                provider.addOrUpdate(AmazonResponse.parse(data));
              } catch (Exception e) { e.printStackTrace(); }
            }
      
          });
          
      } catch (Exception e) { e.printStackTrace(); }
    
    });

  }

}
