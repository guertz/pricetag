package com.guerzonica.app.picodom.components;

import com.guerzonica.app.http.interfaces.RequestHandler;
import com.guerzonica.app.http.models.AmazonResponse;
import com.guerzonica.app.picodom.components.SearchField;
import com.guerzonica.app.storage.ProductsProvider;
import com.guerzonica.app.storage.exceptions.NotFoundException;
import com.guerzonica.app.storage.models.Offer;
import com.guerzonica.app.storage.models.Product;
import com.guerzonica.app.storage.models.ProductPrices;


public class AmazonSearchField extends SearchField {

  public AmazonSearchField() {

    super("ASIN Prodotto");
    this.getActionButton().setOnAction(action -> {

      try {
        ProductsProvider provider = ProductsProvider.getProvider();

        try {
          // collection.find() is better
          // no acces db except provider?
          Product item = new Product();
            item.setAsin(this.getContent());
            item.READ();

          // this.modal = new Modal(
          //   "Ricerca prodotto",
          //   super.wrapper,
          //   new Label("L'elemento selezionato è gia presente nella lista")
          // );

        } catch(NotFoundException e) {

          provider.fetchAmazonHttp(this.getContent(), new RequestHandler () {

            // new item push handler
            // add error handler (!= 200)
            @Override
            public void handle(String data) {

              try {
                Offer x = AmazonResponse.parse(data);

                ProductPrices resultSet = new ProductPrices();
                  resultSet.prices.add(x);
                  resultSet.product = x.getProduct();

                provider.addItem(resultSet);

              } catch(Exception e) { e.printStackTrace(); }

            }

          });

        }

      } catch(Exception e) { e.printStackTrace(); }
    });
  }

}
