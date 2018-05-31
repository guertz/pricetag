package com.guerzonica.app.picodom.pages;

import java.sql.SQLException;
import java.net.URISyntaxException;
import java.io.IOException;
import java.net.URI;
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
import javafx.application.Application;

import javafx.application.HostServices;
import javafx.application.Platform;

public class ProductsPage extends ListPage<ProductPrices, ProductItem> implements ListPage.Listener<ProductPrices> {

  public static String title = "Products List";
  public static String cssClass = "products-List";

  private ListView<ProductItem> list;
  private ImageButton delete;
  private ImageButton link;
  private ProductPrices selectedProduct = null;

  public ProductsPage(Stage stage) throws Exception {
    super(stage, ProductsProvider.getProvider().collection, new ProductItem());

    this.delete = new ImageButton("icons/close.png");
    this.delete.setVisible(false);
    this.link = new ImageButton("icons/goto.png");
    this.link.setVisible(false);

      // // Iterator<ProductPrices> pp = provider.collection.iterator();
      //
      // // this.list = new ListView<ProductItem>();
      // // this.list.getStyleClass().add(ProductsPage.cssClass);
      //
      // while(pp.hasNext()) {
      //   // this.list.getItems().add(new ProductItem(pp.next()));
      // }
      //
      // super.setList(list);

    this.initListeners();
    super.toolbar.setTitle(ProductsPage.title);
    super.toolbar.getRightNode().setSpacing(15);
    super.toolbar.getRightNode().getChildren().addAll(link, delete);

  }

  public void handle(ProductPrices item) {
    System.out.println(item.product.getName());
    if(!this.delete.isVisible())
      this.delete.setVisible(true);
    if(!this.link.isVisible()) this.link.setVisible(true);
      this.selectedProduct = item;
  }

  private void initListeners(){
    super.setListener(this);

    this.delete.setOnAction(action -> {
      //this.list.getSelectionModel().getSelectedItem() delete item
      try{
      selectedProduct.product.DELETE();
      } catch(SQLException e){
        System.err.println("Can't delete product");
        e.printStackTrace();
      }

    });

    this.link.setOnAction(action -> {
      if( Desktop.isDesktopSupported() ){
        new Thread(() -> {
           try {
               Desktop.getDesktop().browse( new URI( selectedProduct.product.getLink() ) );
           } catch (IOException | URISyntaxException e1) {
               e1.printStackTrace();
           }
       }).start();
      }
    });
  }

}
