package com.guerzonica.app.picodom.pages;

import java.sql.SQLException;
import java.net.URISyntaxException;
import java.io.IOException;
import java.net.URI;
import java.awt.Desktop;

import com.guerzonica.app.picodom.components.ImageButton;
import com.guerzonica.app.picodom.components.ProductItem;
import com.guerzonica.app.picodom.pages.base.ListPage;
import com.guerzonica.app.storage.models.ProductPrices;

import javafx.stage.Stage;

// TODO: Fetch item realtime
public class ProductsPage extends ListPage<ProductPrices> {

  public static final String title    = "Products List";
  public static final String cssClass = "products-List";

  private ImageButton delete;
  private ImageButton link;

  private ProductPrices item;

  public ProductsPage(Stage stage) {
    super(stage, ProductItem.class);

    this.delete = new ImageButton("icons/close.png") {{
      setVisible(false); 
      setOnAction(e -> onButtonDelete());
    }};

    this.link = new ImageButton("icons/goto.png") {{ 
      setVisible(false); 
      setOnAction(e -> onButtonOpen());
    }};

    super.toolbar.setTitle(title);
    super.toolbar.getRightNode().setSpacing(15);
    super.toolbar.getRightNode().getChildren().addAll(link, delete);

  }

  @Override
  public void onEvent(ProductPrices item){

    this.item = item;
    
    if(!this.delete.isVisible())
      this.delete.setVisible(true);
    if(!this.link.isVisible()) 
      this.link.setVisible(true);
  }

  public void onButtonDelete() {
    try{
      this.item.getProduct().DELETE();
    } catch(SQLException e){
      e.printStackTrace();
    }
  }

  public void onButtonOpen() {
    if( Desktop.isDesktopSupported() ){
      new Thread(() -> {
        try {
          Desktop.getDesktop().browse(new URI(item.getProduct().getLink()));
        } catch (IOException | URISyntaxException e) {
          e.printStackTrace();
        }
     }).start();
    }
  }

}
