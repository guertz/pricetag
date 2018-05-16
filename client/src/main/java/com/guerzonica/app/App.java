package com.guerzonica.app;
import com.guerzonica.app.http.Request;
import com.guerzonica.app.http.HttpClient;
import com.guerzonica.app.pages.base.Page;
import javafx.stage.Stage;
import com.guerzonica.app.pages.DashboardPage;
import com.guerzonica.app.providers.PageProvider;
import com.guerzonica.app.providers.ProductsProvider;
import io.reactivex.functions.Consumer;
import com.guerzonica.app.models.data.*;
import javafx.application.Application;
import com.guerzonica.app.models.amazon.AmazonRequest;
import com.guerzonica.app.models.amazon.AmazonResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

        try {
            AmazonRequest request = new AmazonRequest();
                request.setItedId("B072K2TQX4");
               request.setResponseGroup("Images,ItemAttributes,OfferFull");

            Request<String> amazonHttp = new HttpClient().makeClient(Api.class).request(request.getRequestUri());
                
                amazonHttp.start(new RequestListener<String>(){
                    @Override
                    public void onResponse(String data) {
                        // handle constructor + sql definition better
                        ProductDetails x = new ProductDetails();

                        try { x = AmazonResponse.parse(data); }
                        catch(Exception e) { e.printStackTrace(); }

                        Gson serializer = new GsonBuilder().setPrettyPrinting().create();
                        System.out.println(serializer.toJson(x));
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
