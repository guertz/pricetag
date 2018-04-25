package com.guerzonica.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.guerzonica.app.components.Graph;
import com.guerzonica.app.providers.Prices;
import com.guerzonica.app.providers.Products;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Pricetag");

        ScrollPane scrollPane = new ScrollPane();
            scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        GridPane root = new GridPane();

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

                root.add(chart, 0, product.getInt("id") - 1);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        scrollPane.setContent(root);
        Scene scene  = new Scene(scrollPane);

        primaryStage.setScene(scene);

        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);

        primaryStage.setMaxHeight(800);
        primaryStage.setMaxWidth(1000);

        primaryStage.show();

    }
}