package com.guerzonica.app.picodom.pages;

import java.util.Map;

import com.guerzonica.app.App;
import com.guerzonica.app.picodom.components.ImageButton;
import com.guerzonica.app.picodom.components.Modal;
import com.guerzonica.app.picodom.pages.base.ListPage;
import com.guerzonica.app.storage.ProductsProvider;
import com.guerzonica.app.storage.models.ProductPrices;
import io.reactivex.functions.Consumer;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.application.Platform;


import  com.guerzonica.app.picodom.components.*;


// @TODO: Fetch item realtime
public class DashboardPage extends ListPage<ProductPrices> {

  public static final String title    = "Dashboard";
  public static final String cssClass = "dashboard";

  protected Modal modal;

  public DashboardPage(Stage stage) {

    super(stage, CellGraph.class);
    super.toolbar.setTitle(title);

    ImageButton listButton = new ImageButton("icons/list.png", 30, 30);
    ImageButton info = new ImageButton("icons/info.png", 30, 30);
    listButton.setOnAction(action -> {
        try {
          App.pageController.push(new ProductsPage(super.getStage()));
        } catch(Exception e) { e.printStackTrace(); }
    });

    info.setOnAction(action -> {
      try {
        App.pageController.push(new AboutPage(super.getStage()));
      } catch(Exception e) { e.printStackTrace(); }
    });

    super.toolbar.getRightNode().getChildren().addAll(info, listButton, new AmazonSearchField());
    super.list.getStyleClass().add("list-simple");

    // VBox.setVgrow(listItems, Priority.ALWAYS);
    // ReadOnlyDoubleProperty width = super.getStage().widthProperty();

    ListView<ProductPrices> listRef = super.list;

    try {
      ProductsProvider provider = ProductsProvider.getProvider();
        provider.getCollection()
          .subscribe(new Consumer<Map<String, ProductPrices>>() {

            @Override
            public void accept(Map<String, ProductPrices> t) throws Exception {

              Platform.runLater(new Runnable() {
                @Override
                public void run() {
                  
                  listRef.getItems().clear();
                  for(Map.Entry<String, ProductPrices> entry : t.entrySet()) {
                    listRef.getItems().add(entry.getValue());
                  }

                }
              });				
            }

          });

    } catch(Exception e) { }
  }

}
