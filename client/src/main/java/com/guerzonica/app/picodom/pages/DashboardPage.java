package com.guerzonica.app.picodom.pages;

import java.util.Vector;

import com.guerzonica.app.App;
import com.guerzonica.app.http.interfaces.RequestHandler;
import com.guerzonica.app.http.models.AmazonResponse;
import com.guerzonica.app.picodom.components.Graph;
import com.guerzonica.app.picodom.components.ImageButton;
import com.guerzonica.app.picodom.components.Modal;
import com.guerzonica.app.picodom.components.SearchField;
import com.guerzonica.app.picodom.pages.base.ListPage;
import com.guerzonica.app.storage.ProductsProvider;
import com.guerzonica.app.storage.exceptions.NotFoundException;
import com.guerzonica.app.storage.models.Offer;
import com.guerzonica.app.storage.models.Product;
import com.guerzonica.app.storage.models.ProductPrices;

import io.reactivex.functions.Consumer;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.application.Platform;

class GraphCell extends ListCell<ProductPrices> {

  @Override
  public void updateItem(ProductPrices item, boolean empty) {

    if(!empty) {
      super.updateItem(item, empty);

      final Graph chart = new Graph(
        new CategoryAxis(),
        new NumberAxis(),
        item
      );

      chart.setMaxHeight(60);
      chart.setMinWidth(100);

      setGraphic(chart);

      //setPrefHeight(chart.getHeight());
      prefHeightProperty().bind(chart.heightProperty());
    }

    // chart.minWidthProperty().bind(width);

  }

}

class AmazonSearchField extends SearchField {

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

// TODO: Fetch item realtime
public class DashboardPage extends ListPage<ProductPrices> {

  public static final String title    = "Dashboard";
  public static final String cssClass = "dashboard";

  protected Modal modal;

  public DashboardPage(Stage stage) {

    super(stage, GraphCell.class);
    super.toolbar.setTitle(title);

    ImageButton listButton = new ImageButton("icons/list.png", 30, 30);

      listButton.setOnAction(action -> {
        try {
          App.pageController.push(new ProductsPage(super.getStage()));
        } catch(Exception e) { }
      });
    
    super.toolbar.getRightNode().getChildren().addAll(listButton, new AmazonSearchField());
    super.list.getStyleClass().add("list-simple");

    // VBox.setVgrow(listItems, Priority.ALWAYS);
    // ReadOnlyDoubleProperty width = super.getStage().widthProperty();

    ListView<ProductPrices> listRef = super.list;
    
    try {
      ProductsProvider provider = ProductsProvider.getProvider();
        provider.collection$
          .subscribe(new Consumer<Vector<ProductPrices>>() {

            @Override
            public void accept(Vector<ProductPrices> list) throws Exception {
              Platform.runLater(new Runnable() {
                @Override 
                public void run() {
                  listRef.getItems().clear();
                  listRef.getItems().addAll(list);
                }
              });
			      }

          });

    } catch(Exception e) { }
  }

}
