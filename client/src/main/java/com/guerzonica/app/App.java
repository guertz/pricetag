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
    public static void main(String[] args) {
        launch(args);
    }
    public void bootstrap(){

      // System.out.println(Font.loadFont(App.class.getResource("fonts/OpenSans.ttf").toExternalForm(), 10));
      // ClassLoader classloader = Thread.currentThread().getContextClassLoader();
      // InputStream is = classloader.getResourceAsStream("fonts/OpenSans.ttf");
      // Font.loadFont(is, 10);

      //
      // Toolbar toolbar = new Toolbar("Title");
      // toolbar.setFullSize(stage);
      //
      // container.getChildren().add(toolbar);
      // Scene n  = new Scene(container);
      // n.getStylesheets().add("css/pricetheme.css");
      // stage.setScene(n);
      // stage.show();
    }
    @Override
    public void start(Stage stage) {

        //Init pages
        bootstrap();


        pageController.push(new DashboardPage(stage));


        stage.show();
        //
        // pageController.getActivePage().show();
        // pageController.showActivePage();
    }
}
