package com.guerzonica.app;
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
        this.toolbar = new BorderPane();
        this.toolbar.getStyleClass().addAll("container","toolbar", "primary");
        HBox t = new HBox();
        t.setSpacing(10);
        t.getStyleClass().add("container");
        t.setPickOnBounds(false);
        t.setMaxHeight(68);
        Label test = new Label("Title");
        Image image = new Image("icons/back.png");
        ImageView iv = new ImageView(image);
        iv.setFitWidth(25);
        iv.setFitHeight(25);
        Button button = new Button("", iv);
        button.getStyleClass().addAll("button", "fab");
        t.getChildren().addAll(button, test);
        toolbar.setLeft(t);
        // this.toolbar.prefWidthProperty().bind(stage.widthProperty());
        this.toolbar.setMinWidth(stage.getMinWidth());
        this.toolbar.setMaxHeight(68);
        // this.toolbar.setPrefHeight(-1);
        Scene n  = new Scene(this.toolbar);
        n.getStylesheets().add("css/pricetheme.css");
        stage.setScene(n);
        stage.show();
        // try {
          // pageController.push(new DashboardPage(primaryStage));
        // } catch(IllegalAccessException e) { e.printStackTrace(); }
        //   catch(InstantiationException e) { e.printStackTrace(); }



        // pageController.getActivePage().show();
        // pageController.showActivePage();
    }
}
