package com.guerzonica.app.picodom.pages;

import java.net.URL;
import java.awt.Desktop;
import java.util.Iterator;

import com.guerzonica.app.picodom.components.ImageButton;
import com.guerzonica.app.picodom.components.ProductItem;
import com.guerzonica.app.picodom.pages.base.ListPage;
import com.guerzonica.app.storage.ProductsProvider;
import com.guerzonica.app.storage.models.Product;
import com.guerzonica.app.storage.models.ProductPrices;

import javafx.stage.Stage;
import javafx.scene.control.ListView;


public class ProductsPage extends ListPage implements ListPage.Listener<ProductItem> {

  public static String title = "Products List";
  public static String cssClass = "products-List";

  private ListView<ProductItem> list;
  private ImageButton delete;
  private ImageButton link;
  private Product selectedProduct = null;

  public ProductsPage(Stage stage) {

    super(stage);
    this.delete = new ImageButton("icons/close.png");
    this.delete.setVisible(false);
    this.link = new ImageButton("icons/goto.png");
    this.link.setVisible(false);

    try{
      ProductsProvider provider = ProductsProvider.getProvider();
      Iterator<ProductPrices> pp = provider.collection.iterator();

      this.list = new ListView<ProductItem>();
      this.list.getStyleClass().add(ProductsPage.cssClass);

      while(pp.hasNext()) {
        this.list.getItems().add(new ProductItem(pp.next().product));
      }

      super.setList(list);

    } catch(Exception e) { }
    
    this.initListeners();
    super.toolbar.setTitle(ProductsPage.title);
    super.toolbar.getRightNode().setSpacing(15);
    super.toolbar.getRightNode().getChildren().addAll(link, delete);

  }

  public void handle(ProductItem item) {
    if(!this.delete.isVisible()) 
      this.delete.setVisible(true);
    if(!this.link.isVisible()) this.link.setVisible(true);
      this.selectedProduct = this.list.getSelectionModel().getSelectedItem().getProduct();
  }

  private void initListeners(){
    super.setListener(this);
    
    this.delete.setOnAction(action -> {
      //this.list.getSelectionModel().getSelectedItem() delete item
    });

    this.link.setOnAction(action -> {
      Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
      if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
        try {
          System.out.println(this.selectedProduct.getLink());
         desktop.browse(new URL(this.selectedProduct.getLink()).toURI());
        } catch (Exception e) {
          System.err.println("OPEN PRODUCT LINK FAILED");
         e.printStackTrace();
       }
     }
    });
  }

}
