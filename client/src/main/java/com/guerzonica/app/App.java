package com.guerzonica.app;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.guerzonica.app.env.Env;
import com.guerzonica.app.picodom.Navigation;
import com.guerzonica.app.picodom.pages.DashboardPage;
import javafx.stage.Stage;
import javafx.application.Application;

/**
 * App instance
 *
 * @author Matteo Guerzoni, Singh Amarjot
 */
public class App extends Application {

    /** Stack Navigator */
    public static Navigation pageController = new Navigation();

    /**
     * App main
     *
     * @param args arguments to specify database file name.
     */
    public static void main(String[] args) {
        if(args.length > 0) {
            String home = System.getProperty("user.home");
            Path   file = Paths.get(home, args[0]);

            Env.setPath(file.toString());
        }

        launch(args);
    }

    /**
     * Create the JavaFx application
     *
     * @param stage The stage to display
     */
    @Override
    public void start(Stage stage) {
        pageController.push(new DashboardPage(stage));
        stage.show();
    }
}
