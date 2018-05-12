package com.guerzonica.app.pages;

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
  private Vector<ProductDetails> results;
  private ListView<ProductItem> list;
  private ImageButton delete;

  public ProductsPage(Stage stage){
    super(stage);
    this.delete = new ImageButton("icons/close.png");
    this.delete.getStyleClass().add("primary");
    this.delete.setVisible(false);
    try{
      ProductsProvider provider = ProductsProvider.getProvider();
      this.results = provider.getAll();
      this.list = new ListView<ProductItem>();
      for(Product p : results){
        this.list.getItems().add(new ProductItem(p));
      }
      super.setList(list);
    }catch(SQLException e){

    }
    this.initListeners();
    super.toolbar.getRightNode().getChildren().add(delete);

  }
  public void onItemSelect(ProductItem item){
    if(!this.delete.isVisible()) this.delete.setVisible(true);
  }

  private void initListeners(){
    super.setListener(this);
    this.delete.setOnAction(action -> {
      //this.list.getSelectionModel().getSelectedItem() delete item
    });
  }

}
