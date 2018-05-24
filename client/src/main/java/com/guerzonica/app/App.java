package com.guerzonica.app;
import com.guerzonica.app.http.Request;
import com.guerzonica.app.http.interfaces.Body;
import com.guerzonica.app.http.interfaces.RequestHandler;
import com.guerzonica.app.http.models.AmazonRequest;
import com.guerzonica.app.http.models.AmazonResponse;
import com.guerzonica.app.http.HttpClient;
import com.guerzonica.app.picodom.Navigation;
import com.guerzonica.app.picodom.pages.DashboardPage;
import com.guerzonica.app.picodom.pages.base.Page;
import com.guerzonica.app.storage.ProductsProvider;
import com.guerzonica.app.storage.models.Offer;
import com.guerzonica.app.storage.models.ProductPrices;

import javafx.stage.Stage;
import javafx.application.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    public void start(Stage stage) {

        //Init pages
        bootstrap();
        pageController.push(new DashboardPage(stage));

        try {
            AmazonRequest request = new AmazonRequest();
                request.setItedId("B072K2TQX4");
                request.setResponseGroup("Images,ItemAttributes,OfferFull");

            Request<String> amazonHttp = new HttpClient().makeClient(Body.class).request(request.getRequestUri());
                
                amazonHttp.start(new RequestHandler(){
                    @Override
                    public void handle(String data) {
                        Offer x = new Offer();

                        try { 
                            x = AmazonResponse.parse(data); 
                            ProductPrices resultSet = new ProductPrices();
                                resultSet.prices.add(x);
                                resultSet.product = x.getProduct();

                            ProductsProvider provider = ProductsProvider.getProvider();
                            provider.collection.add(resultSet);

                        }
                        catch(Exception e) { e.printStackTrace(); }

                        Gson serializer = new GsonBuilder().setPrettyPrinting().create();
                        System.out.println(serializer.toJson(x));
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
