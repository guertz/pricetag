package com.guerzonica.app;
import com.guerzonica.app.picodom.Navigation;
import com.guerzonica.app.picodom.pages.DashboardPage;
import com.guerzonica.app.picodom.pages.base.Page;
import javafx.stage.Stage;
import javafx.application.Application;

public class App extends Application {

    // private DomPage<HBox, GridPane, HBox> dashboard;
    // private PageProvider<Page> navCtrl = new PageProvider<Page>();

    public static Navigation<Page> pageController = new Navigation<Page>();

    public static void main(String[] args) {
        launch(args);
    }

    public void bootstrap(){

    }

    @Override
    @SuppressWarnings("all")
    public void start(Stage stage) {
        bootstrap();
        pageController.push(new DashboardPage(stage));


        stage.show();
    }
}
