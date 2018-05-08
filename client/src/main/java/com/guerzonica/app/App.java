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

public class App extends Application {
    // private DomPage<HBox, GridPane, HBox> dashboard;
    // private PageProvider<Page> navCtrl = new PageProvider<Page>();
    public static PageProvider<Page> pageController = new PageProvider<Page>();

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
    public void start(Stage primaryStage) {

        //Init pages
        bootstrap();
    
        // try {
          pageController.push(new DashboardPage(primaryStage));
        // } catch(IllegalAccessException e) { e.printStackTrace(); }
        //   catch(InstantiationException e) { e.printStackTrace(); }



        pageController.getActivePage().show();
        // pageController.showActivePage();
    }
}
