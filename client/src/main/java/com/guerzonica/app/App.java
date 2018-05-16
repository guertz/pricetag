package com.guerzonica.app;
import com.guerzonica.app.http.Request;
import com.guerzonica.app.http.HttpClient;
import com.guerzonica.app.pages.base.Page;
import javafx.stage.Stage;
import com.guerzonica.app.pages.DashboardPage;
import com.guerzonica.app.models.xml.Test;
import com.guerzonica.app.providers.PageProvider;
import com.guerzonica.app.providers.ProductsProvider;
import io.reactivex.functions.Consumer;
import com.guerzonica.app.models.data.*;
import javafx.application.Application;
import com.guerzonica.app.models.amazon.AmazonRequest;
import com.guerzonica.app.http.Api;
import com.guerzonica.app.http.RequestListener;

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

        // AmazonHttp.makeRequest();
        Test.run();
        
        // HttpCLient.makeClient()
        // AmazonHttp request = new AmazonHttp().makeClient(Api.class).request(new AmazonRequest().getRequestUri());
        
        try {
          AmazonRequest request = new AmazonRequest();
               request.setItedId("B072K2TQX4");
               request.setResponseGroup("Images,ItemAttributes,OfferFull");
          Request<String> thread = new HttpClient().makeClient(Api.class).request(request.getRequestUri());
          thread.start(new RequestListener<String>(){

            @Override
            public void onResponse(String data) {
              System.out.println(data);
              //you have to implement a way like you did with TypeToken interface. For now i set to String for semplicity.
            }
          });
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
