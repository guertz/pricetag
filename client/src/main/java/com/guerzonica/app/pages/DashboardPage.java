package com.guerzonica.app.pages;
//https://stackoverflow.com/questions/40750526/javafx-best-practice-for-navigating-between-ui-screens

import com.guerzonica.app.pages.DomPage;
import com.guerzonica.app.providers.PageProvider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import java.util.Vector;
import com.guerzonica.app.components.Graph;
import com.guerzonica.app.models.data.ProductDetails;
import com.guerzonica.app.providers.ProductsProvider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import com.guerzonica.app.components.Modal;
import com.guerzonica.app.App;

// import javafx.geometry.Insets;
// import javafx.scene.layout.Region;
// import javafx.scene.text.Text;
// import javafx.scene.text.TextAlignment;
// import javafx.scene.text.FontWeight;
// import javafx.scene.text.Font;
public class DashboardPage extends DomPage<HBox, GridPane, HBox> {
  // private Scene scene;

  // private static PageProvider<Page> pageCtrl = PageProvider.getInstance();

  public DashboardPage(Stage stage)
  throws InstantiationException, IllegalAccessException {
    super(stage, new HBox(), new GridPane(), new HBox());
    header();
    body();
    super.footer();

    // welcome();
  }
  public void launch(){

  }
  @Override
  public void header(){
    super.header(); // get predefined style
    HBox header = super.getHeader();

    header.setSpacing(10);

    final TextField search = new TextField();
    search.setPromptText("Insert Product ID");
    search.setMinWidth(300);
    search.getStyleClass().addAll("text-field", "flat");

    final Button trackButton = new Button("Track");
    trackButton.setMinWidth(100);
    trackButton.getStyleClass().addAll("button", "flat", "padding-both");

    trackButton.setOnAction(action -> {
      Label text = new Label("Hello, this is PriceTag.");
      text.setWrapText(true);
      Modal j = new Modal("Welcome", this.getRoot(), text );
      // try{
      //   App.pageController.push(
      //     new DomPage<HBox,VBox,HBox>(
      //       super.getStage(),
      //       new HBox(),
      //       new VBox(),
      //       new HBox()), true);
      // } catch(Exception e ){}
    });

    header.getChildren().addAll(search, trackButton);
  }
  @Override
  public void body(){
    super.body();
    try {
        ProductsProvider provider = ProductsProvider.getProvider();
        Vector<ProductDetails> results = provider.getAll();
        results.forEach(p -> {
            final Graph chart = new Graph(
                new CategoryAxis(),
                new NumberAxis(),
                p
            );

                chart.minWidthProperty().bind(super.getStage().widthProperty());
                chart.setMaxHeight(60);

                this.getBody().add(chart, 0, p.getId()); // 1 + x -1
                // (this.getClass().cast(App.pageController.getActivePage()))

        });

    } catch (Exception e) {
        e.printStackTrace();
    }
  }

  public void welcome(){

  }
  // public void show(){
  //   super.show();
  // }
  // public <? extends Node> getBody(){ return super.body; }
  //
  // public BorderPane getRoot(){ return this.root; }

  // private GridPane body(){
  //   body = new GridPane();
  //   body.setHgap(10);
  //   body.setVgap(10);
  //   body.setPadding(new Insets(5, 5, 5, 5));
  //   body.getStyleClass().add("body");
  //   Text title = new Text("Charts");
  //   title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
  //   title.setTextAlignment(TextAlignment.CENTER);
  //   this.body.add(title, 1, 0);
  //   return this.body;
  // }
  // private HBox header() {
  //   HBox row = new HBox();
  //   row.setPadding(new Insets(10, 10, 10, 10));
  //   row.setSpacing(10);
  //   row.getStyleClass().add("header");
  //   //set style
  //   final TextField search = new TextField();
  //   search.setPromptText("Insert Product ID");
  //   search.setMinWidth(300);
  //   search.getStyleClass().addAll("text-field", "flat");
  //   final Button trackButton = new Button("Track");
  //   trackButton.setMinWidth(100);
  //   trackButton.getStyleClass().addAll("button", "flat", "padding-both");
  //   row.getChildren().addAll(search, trackButton);
  //
  //   return row;
  // }

}
