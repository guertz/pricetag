package com.guerzonica.app.picodom.pages;

import com.guerzonica.app.picodom.components.cell.CellGraph;
import com.guerzonica.app.picodom.components.field.AmazonSearchField;
import java.util.Map;
import com.guerzonica.app.App;
import com.guerzonica.app.picodom.components.ImageButton;
import com.guerzonica.app.picodom.pages.base.ListPage;
import com.guerzonica.app.storage.ProductsProvider;
import com.guerzonica.app.storage.models.ProductPrices;
import io.reactivex.functions.Consumer;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.application.Platform;

/**
* This is the page that handles the dashboard. It takes care to show the list of graphs,
* get a product by ASIN and permits navigation through other pages.
* Items are fetched in realt-time and then they are syncronized in the view.
*
* @author Singh Amarjot, Matteo Guerzoni
*
* @see com.guerzonica.app.picodom.pages.base.Page
* @see com.guerzonica.app.picodom.pages.base.ListPage
* @see com.guerzonica.app.storage.ProductsProvider
*/

public class DashboardPage extends ListPage<ProductPrices> {

  public static final String title    = "Dashboard";
  public static final String cssClass = "dashboard";

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

    super.unselectable();
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
