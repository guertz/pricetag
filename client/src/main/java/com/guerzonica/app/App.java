package com.guerzonica.app;
import com.guerzonica.app.components.ToolbarEvent;
import com.guerzonica.app.components.Toolbar;
import com.guerzonica.app.pages.ListPage;
import java.io.InputStream;
import com.guerzonica.app.pages.Page;
import javafx.stage.Stage;
import com.guerzonica.app.pages.DashboardPage;
import com.guerzonica.app.providers.PageProvider;
import javafx.application.Application;
import javafx.scene.text.Font;

import javafx.scene.control.ListView;

import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class App extends Application {
    // private DomPage<HBox, GridPane, HBox> dashboard;
    // private PageProvider<Page> navCtrl = new PageProvider<Page>();
    public static PageProvider<Page> pageController = new PageProvider<Page>();
    private BorderPane toolbar;
    public static void main(String[] args) {
        launch(args);
    }
    public void bootstrap(){

      // System.out.println(Font.loadFont(App.class.getResource("fonts/OpenSans.ttf").toExternalForm(), 10));
      ClassLoader classloader = Thread.currentThread().getContextClassLoader();
      InputStream is = classloader.getResourceAsStream("fonts/OpenSans.ttf");
      Font.loadFont(is, 10);


    }
    @Override
    public void start(Stage stage) {

        //Init pages
        bootstrap();
        stage.setTitle("Pricetag");
        stage.setMaxHeight(800);
        stage.setMaxWidth(1000);
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        VBox container = new VBox();
        // container.prefWidthProperty().bind(stage.widthProperty());
        // // container.setPrefHeight(value);
        // this.toolbar = new BorderPane();
        // this.toolbar.getStyleClass().addAll("container","toolbar", "primary");
        // HBox t = new HBox();
        // t.setSpacing(10);
        // t.getStyleClass().add("container");
        // t.setPickOnBounds(false);
        // Label test = new Label("Title");
        // test.getStyleClass().add("heading");
        // Image image = new Image("icons/back.png");
        // ImageView iv = new ImageView(image);
        // iv.setFitWidth(25);
        // iv.setFitHeight(25);
        //
        // Button button = new Button("", iv);
        // button.getStyleClass().addAll("fab", "primary");
        // button.setShape(new Circle(20));
        // t.getChildren().addAll(button, test);
        // toolbar.setLeft(t);
        // this.toolbar.setMinWidth(stage.getMinWidth());
        // this.toolbar.prefWidthProperty().bind(container.widthProperty());
        //
        // container.getChildren().add(this.toolbar);

        Toolbar toolbar = new Toolbar("Title");
        toolbar.addEventFilter(ToolbarEvent.BACK_CLICK, e -> {

          e.consume();

        });
        toolbar.setMinWidth(stage.getMinWidth());
        toolbar.prefWidthProperty().bind(container.widthProperty());
        container.getChildren().add(toolbar);
        Scene n  = new Scene(container);
        n.getStylesheets().add("css/pricetheme.css");
        stage.setScene(n);
        stage.show();

        toolbar.setBackButton();
        // try {
          // pageController.push(new DashboardPage(primaryStage));
        // } catch(IllegalAccessException e) { e.printStackTrace(); }
        //   catch(InstantiationException e) { e.printStackTrace(); }



        // pageController.getActivePage().show();
        // pageController.showActivePage();
    }
}
