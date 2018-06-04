package com.guerzonica.app.picodom.pages;

import java.util.Map;

import javafx.scene.control.ListView;

import java.net.URISyntaxException;
import java.io.IOException;
import java.net.URI;
import java.awt.Desktop;

import com.guerzonica.app.App;
import com.guerzonica.app.picodom.components.CellProduct;
import com.guerzonica.app.picodom.components.ImageButton;
import com.guerzonica.app.picodom.pages.base.ListPage;
import com.guerzonica.app.storage.ProductsProvider;
import com.guerzonica.app.storage.models.Product;
import com.guerzonica.app.storage.models.ProductPrices;

import io.reactivex.functions.Consumer;
import javafx.stage.Stage;

public class ProductsPage extends ListPage<Product> {

  public static final String title    = "Products List";
  public static final String cssClass = "products-List";

  private ImageButton delete;
  private ImageButton link;
  
  private Product item;

  public ProductsPage(Stage stage) {
    super(stage, CellProduct.class);

    delete = new ImageButton("icons/close.png");
      delete.setVisible(false); 
      delete.setOnAction(e -> onButtonDelete());

    link = new ImageButton("icons/goto.png"); 
      link.setVisible(false); 
      link.setOnAction(e -> onButtonOpen());

    super.toolbar.setTitle(title);
    super.toolbar.getRightNode().setSpacing(15);
    super.toolbar.getRightNode().getChildren().addAll(link, delete);

    ListView<Product> listRef = super.list;
    
    try {
      ProductsProvider provider = ProductsProvider.getProvider();
        provider.getCollection()
          .take(1)
          .subscribe(new Consumer<Map<String, ProductPrices>>() {

            @Override
            public void accept(Map<String, ProductPrices> t) throws Exception {

              for(Map.Entry<String, ProductPrices> entry : t.entrySet()) {
                listRef.getItems().add(entry.getValue());
              }
			
            }

          });

    } catch(Exception e) { }

  }

  @Override
  public void onEvent(Product item){

    this.item = item;
    
    if(!this.delete.isVisible())
      this.delete.setVisible(true);
    if(!this.link.isVisible()) 
      this.link.setVisible(true);
  }

  public void onButtonDelete() {
    try {
      ProductsProvider provider = ProductsProvider.getProvider();
        provider.deleteProduct(item, true, true).subscribe();
        App.pageController.pop();
        
    } catch (Exception e) { }
  }

  public void onButtonOpen() {
    if( Desktop.isDesktopSupported() ){
      new Thread(() -> {
        try {
          Desktop.getDesktop().browse(new URI(item.getLink()));
        } catch (IOException | URISyntaxException e) {
          e.printStackTrace();
        }
     }).start();
    }
  }

}
