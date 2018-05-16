package com.guerzonica.app.pages;
//https://stackoverflow.com/questions/40750526/javafx-best-practice-for-navigating-between-ui-screens

import com.guerzonica.app.components.SearchField;
import com.guerzonica.app.components.Modal;
import com.guerzonica.app.components.ImageButton;
import com.guerzonica.app.pages.base.ListPage;
import com.guerzonica.app.App;
import com.guerzonica.app.pages.base.DomPage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import java.util.Vector;
import com.guerzonica.app.components.Graph;
import com.guerzonica.app.models.data.ProductDetails;
import com.guerzonica.app.providers.ProductsProvider;


import javafx.scene.control.Label;
public class DashboardPage extends DomPage<HBox, GridPane, HBox> {
  // private Scene scene;
  private Vector<ProductDetails> results;
  // private static PageProvider<Page> pageCtrl = PageProvider.getInstance();
  public static String title = "Dashboard";
  public static String cssClass = "dashboard";

  private Modal modal;
  // private Toolbar toolbar;
  public DashboardPage(Stage stage) {

    super(stage, new HBox(), new GridPane(), new HBox(), cssClass);
    super.toolbar.setTitle(DashboardPage.title);


    header();
    body();
    super.footer();

    // welcome();
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
  @Override
  public void body(){
    super.body();
    try {
        ProductsProvider provider = ProductsProvider.getProvider();
        this.results = provider.getAll();
        results.forEach(p -> {

            final Graph chart = new Graph(
                new CategoryAxis(),
                new NumberAxis(),
                p
            );

                chart.minWidthProperty().bind(super.getStage().widthProperty());
                chart.setMaxHeight(60);

                // this.getBody().add(chart, 0, p.getId()); // 1 + x -1
                // (this.getClass().cast(App.pageController.getActivePage()))

        });

    } catch (Exception e) {
        e.printStackTrace();
    }
  }

  public void welcome(){

  }

}
