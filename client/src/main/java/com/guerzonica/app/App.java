package com.guerzonica.app;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.guerzonica.app.env.Env;
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
        if(args.length > 0) {
            String home = System.getProperty("user.home");
            Path   file = Paths.get(home, args[0]);

            Env.setPath(file.toString());
        }
        
        launch(args);
    }

    public void bootstrap(){
        
    }

    @Override
    public void start(Stage stage) {
        bootstrap();
        pageController.push(new DashboardPage(stage));
        stage.show();
    }
}
