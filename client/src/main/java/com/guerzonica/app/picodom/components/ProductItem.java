package com.guerzonica.app.picodom.components;

import com.guerzonica.app.storage.models.Product;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//Maybe usage of CellFactory of ListView is better
public class ProductItem extends HBox {
  private Label title;
  private ImageView image;
  private Label price;
  private Product product;
  
  public ProductItem(Product p) {
    super();
    // HBox container = new HBox();
    this.getStyleClass().add("product-item");
    this.setSpacing(15);
    this.title = new Label(p.getName());
    this.title.getStyleClass().add("heading-block");
    Image n = new Image(p.getImage());
    this.image = new ImageView(n);
    this.image.getStyleClass().add("product-image");
    this.image.setFitWidth(150);
    this.image.setFitHeight(150);
    this.image.setPreserveRatio(true);
    this.price = new Label("12$");
    this.price.getStyleClass().add("heading-block");
    VBox right = new VBox();
    right.setSpacing(5);
    right.getStyleClass().add("product-content");
    right.getChildren().addAll(this.title, this.price);
    super.getChildren().addAll(this.image, right);
    this.product = p;
  }

  public Product getProduct() {
    return product;
  }
}
