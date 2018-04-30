package com.guerzonica.app;

import javafx.scene.layout.Region;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;


import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.geometry.Insets;
import java.sql.ResultSet;
import java.util.HashMap;

import com.guerzonica.app.components.Graph;
import com.guerzonica.app.providers.Prices;
import com.guerzonica.app.providers.Products;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    public GridPane body(){
      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(5, 5, 5, 5));
      grid.getStyleClass().add("body");
      Text title = new Text("Charts");
      title.setFont(Font.font("Arial", FontWeight.BOLD, 100));

      grid.add(title, 2, 1);
      return grid;
    }
    public HBox header() {
      HBox row = new HBox();
      row.setPadding(new Insets(10, 10, 10, 10));
      row.setSpacing(10);
      row.getStyleClass().add("header");
      //set style
      final TextField search = new TextField();
      search.setPromptText("Insert Product ID");
      search.setMinWidth(300);
      search.getStyleClass().addAll("text-field", "flat");
      final Button trackButton = new Button("Track");
      trackButton.setMinWidth(100);
      trackButton.getStyleClass().addAll("button", "flat", "padding-both");
      row.getChildren().addAll(search, trackButton);

      return row;
    }
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Pricetag");

        ScrollPane scrollPane = new ScrollPane();
            scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        BorderPane root = new BorderPane();
        root.setTop(header());
        GridPane body = body();
        try {
            Products productsProvider = Products.getProvider();
            Prices   pricesProvider   = Prices.getProvider();

            ResultSet product = productsProvider.getAll();

            while(product.next()) {
                ResultSet prices = pricesProvider
                    .getProductPrices(product.getInt("id"));

                HashMap<String, Number> dataSet = new HashMap<String, Number>();

                while(prices.next()) {
                    dataSet.put(
                        prices.getString("date"),
                        prices.getInt("price")
                    );
                }

                final Graph chart = new Graph(new CategoryAxis(), new NumberAxis(), product.getString("name"), dataSet);

                    chart.minWidthProperty().bind(primaryStage.widthProperty());
                    chart.setMaxHeight(60);

                body.add(chart, 0, product.getInt("id") - 1);
            }
            root.setCenter(body);

        } catch (Exception e) {
            e.printStackTrace();
        }

        scrollPane.setContent(root);
        Scene scene  = new Scene(scrollPane);
        scene.getStylesheets().add("css/pricetheme.css");
        primaryStage.setScene(scene);

        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);

        primaryStage.setMaxHeight(800);
        primaryStage.setMaxWidth(1000);

        primaryStage.show();

    }
}
