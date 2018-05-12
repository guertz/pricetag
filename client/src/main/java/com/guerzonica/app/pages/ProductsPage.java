package com.guerzonica.app.pages;

import java.net.URL;
import java.awt.Desktop;
import com.guerzonica.app.components.ImageButton;
import com.guerzonica.app.interfaces.ItemSelectedListener;
import java.sql.SQLException;
import java.util.Vector;
import com.guerzonica.app.pages.base.ListPage;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.guerzonica.app.models.data.ProductDetails;
import com.guerzonica.app.providers.ProductsProvider;
import javafx.scene.control.ListView;
import com.guerzonica.app.components.ProductItem;
import com.guerzonica.app.models.data.Product;


public class ProductsPage extends ListPage implements ItemSelectedListener<ProductItem>{
  public static String title = "Products List";
  public static String cssClass = "products-List";

  private Vector<ProductDetails> results;
  private ListView<ProductItem> list;
  private ImageButton delete;
  private ImageButton link;
  private Product selectedProduct = null;

  public ProductsPage(Stage stage){
    super(stage);
    this.delete = new ImageButton("icons/close.png");
    this.delete.setVisible(false);
    this.link = new ImageButton("icons/goto.png");
    this.link.setVisible(false);
    try{
      ProductsProvider provider = ProductsProvider.getProvider();
      this.results = provider.getAll();
      this.list = new ListView<ProductItem>();
      this.list.getStyleClass().add(ProductsPage.cssClass);
      for(Product p : results){
        this.list.getItems().add(new ProductItem(p));
      }
      super.setList(list);
    }catch(SQLException e){

    }
    this.initListeners();
    super.toolbar.setTitle(ProductsPage.title);
    super.toolbar.getRightNode().setSpacing(15);
    super.toolbar.getRightNode().getChildren().addAll(link, delete);

  }
  public void onItemSelect(ProductItem item){
    if(!this.delete.isVisible()) this.delete.setVisible(true);
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
