package com.guerzonica.app;
import com.guerzonica.app.pages.base.Page;
import javafx.stage.Stage;
import com.guerzonica.app.pages.DashboardPage;
import com.guerzonica.app.providers.PageProvider;
import com.guerzonica.app.providers.ProductsProvider;
import com.guerzonica.app.providers.Test;

import io.reactivex.functions.Consumer;

import com.guerzonica.app.models.data.*;

import javafx.application.Application;

public class App extends Application {
    // private DomPage<HBox, GridPane, HBox> dashboard;
    // private PageProvider<Page> navCtrl = new PageProvider<Page>();
    public static PageProvider<Page> pageController = new PageProvider<Page>();
    public static void main(String[] args) {
        launch(args);
    }
    public void bootstrap(){

    }
    @Override
    public void start(Stage stage) {

        //Init pages
        bootstrap();


        pageController.push(new DashboardPage(stage));

        try {

            Test.run();
            
            ProductsProvider p = ProductsProvider.getProvider();

               p.getStream()
                .flatMapIterable(x -> x)
                .subscribe(new Consumer<ProductDetails>() {
                    @Override public void accept(ProductDetails item) {
                        System.out.println("Handler: " + item.getId());
                    }
                });


        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.show();
        //
        // pageController.getActivePage().show();
        // pageController.showActivePage();
    }
}
