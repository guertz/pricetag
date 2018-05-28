package com.guerzonica.app.picodom.pages;

import com.guerzonica.app.App;
import com.guerzonica.app.picodom.components.Graph;
import com.guerzonica.app.picodom.components.ImageButton;
import com.guerzonica.app.picodom.components.Modal;
import com.guerzonica.app.picodom.components.SearchField;
import com.guerzonica.app.picodom.pages.base.DomPage;
import com.guerzonica.app.storage.ProductsProvider;
import com.guerzonica.app.storage.models.ProductPrices;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;


import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.util.Callback;

public class DashboardPage extends DomPage<HBox, VBox, HBox> {
  // private Scene scene;

  // private static PageProvider<Page> pageCtrl = PageProvider.getInstance();
  public static String title = "Dashboard";
  public static String cssClass = "dashboard";

  private Modal modal;
  private ListView<ProductPrices> listItems = new ListView<ProductPrices>();
  // private Toolbar toolbar;

  public DashboardPage(Stage stage) {

    super(stage, new HBox(), new VBox(), new HBox(), cssClass);
    super.toolbar.setTitle(DashboardPage.title);

    header();
    body();

    super.footer();

  }

  public void launch(){

  }

  @Override
  public void header(){
    // super.header(); // get predefined style
    // HBox header = super.getHeader();

    //
    // header.setSpacing(10);
    //

    final SearchField search = new SearchField("Insert Amazon link");
    search.getActionButton().setOnAction(action -> {
      this.modal = new Modal("Ricerca prodotto", super.wrapper, new Label("Insert here something"));
    });

    ImageButton listButton = new ImageButton("icons/list.png", 30, 30);

    listButton.setOnAction(action -> {
      App.pageController.push(new ProductsPage(super.getStage()));
    });
    // this.toolbar = new Toolbar(title);
    super.toolbar.getRightNode().getChildren().addAll(listButton, search);
    // super.setToolbar(this.toolbar);

    // header.getChildren().addAll();

  }

  static class PricesCell extends ListCell<ProductPrices> {
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

    PricesCell(){
      //this.setPrefHeight(USE_PREF_SIZE);
      // this.prefHeight(100);
    }
}

  @Override
  public void body(){

    super.body();
    listItems.getStyleClass().add("list-simple");
    listItems.prefHeightProperty().bind(super.body.heightProperty());
    super.getBody().getChildren().addAll(listItems);

    // VBox.setVgrow(listItems, Priority.ALWAYS);
    // ReadOnlyDoubleProperty width = super.getStage().widthProperty();

    try {
      ProductsProvider provider = ProductsProvider.getProvider();
          listItems.setItems(provider.collection);

          listItems.setCellFactory(new Callback<ListView<ProductPrices>,
            ListCell<ProductPrices>>() {
                @Override
                public ListCell<ProductPrices> call(ListView<ProductPrices> list) {
                    return new PricesCell();
                }
            }
        );

    } catch (Exception e) {
        e.printStackTrace();
    }
  }

  public void welcome(){

  }

}
