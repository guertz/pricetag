package com.guerzonica.app;
import com.guerzonica.app.pages.Page;
import javafx.stage.Stage;
import com.guerzonica.app.pages.DashboardPage;
import com.guerzonica.app.providers.PageProvider;
import javafx.application.Application;

// import javafx.scene.chart.NumberAxis;
// import javafx.scene.chart.CategoryAxis;
// import java.util.Vector;
// import com.guerzonica.app.components.Graph;
// import com.guerzonica.app.models.data.ProductDetails;
// import com.guerzonica.app.providers.ProductsProvider;
//
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.GridPane;
// import com.guerzonica.app.pages.DomPage;
// import javafx.scene.control.Button;
// import javafx.scene.Scene;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.ScrollPane.ScrollBarPolicy;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.GridPane;
// import javafx.scene.control.TextField;
// import javafx.scene.text.Text;
// import javafx.scene.text.TextAlignment;
// import javafx.scene.text.FontWeight;
// import javafx.scene.text.Font;
// import javafx.geometry.Insets;

public class App extends Application {
    // private DomPage<HBox, GridPane, HBox> dashboard;
    // private PageProvider<Page> navCtrl = new PageProvider<Page>();
    public static PageProvider<Page> pageController = new PageProvider<Page>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Init pages
        try {
          pageController.push(new DashboardPage(primaryStage));
        } catch(IllegalAccessException e) { e.printStackTrace(); }
          catch(InstantiationException e) { e.printStackTrace(); }



        pageController.getActivePage().show();
    }
}
