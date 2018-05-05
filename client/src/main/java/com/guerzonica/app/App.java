package com.guerzonica.app;

import com.guerzonica.app.pages.DomPage;
import com.guerzonica.app.pages.DashboardPage;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;


import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.geometry.Insets;
import java.util.Vector;

import com.guerzonica.app.components.Graph;
import com.guerzonica.app.models.data.ProductDetails;
import com.guerzonica.app.providers.ProductsProvider;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //
        // ScrollPane scrollPane = new ScrollPane();
        //     scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        //     scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        // BorderPane root = new BorderPane();
        // root.setTop(header());
        // GridPane body = body();

        DomPage page = new DomPage(primaryStage);

        try {
            ProductsProvider provider = ProductsProvider.getProvider();
            Vector<ProductDetails> results = provider.getAll();

            results.forEach(p -> {
                final Graph chart = new Graph(
                    new CategoryAxis(),
                    new NumberAxis(),
                    p
                );

                    chart.minWidthProperty().bind(primaryStage.widthProperty());
                    chart.setMaxHeight(60);

                page.getBody().add(chart, 0, p.getId()); // 1 + x -1

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        page.show();
    }
}
