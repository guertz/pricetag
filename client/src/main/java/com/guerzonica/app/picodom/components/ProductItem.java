package com.guerzonica.app.picodom.components;

import com.guerzonica.app.storage.models.ProductPrices;
import com.guerzonica.app.storage.models.Product;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListCell;

//Maybe usage of CellFactory of ListView is better
public class ProductItem extends ListCell<ProductPrices>  {

  // private Label title;
  // private ImageView image;
  // private Label price;
  // private ProductPrices productPrices;
  // private HBox container;
  @Override
  public void updateItem(ProductPrices item, boolean empty) {
    if(!empty) {
      super.updateItem(item, empty);

      final HBox container = new HBox();
      Product p = item.product;
      container.getStyleClass().add("product-item");
      container.setSpacing(15);
      Label title = new Label(p.getName());
      title.getStyleClass().add("heading-block");
      title.setWrapText(true);
      Image n = new Image(p.getImage());
      ImageView image = new ImageView(n);
      image.getStyleClass().add("product-image");
      image.setFitWidth(150);
      image.setFitHeight(150);
      image.setPreserveRatio(true);
      Label price = new Label(Float.toString(item.prices.get(item.prices.size() - 1).getPrice()));
      price.getStyleClass().add("heading-block");
      VBox right = new VBox();
      right.setSpacing(5);
      right.getStyleClass().add("product-content");
      right.getChildren().addAll(title, price);
      container.getChildren().addAll(image, right);
      // this.productPrices = item;

      super.prefWidthProperty().bind(super.getListView().widthProperty());
      setGraphic(container);

      //setPrefHeight(chart.getHeight());
    }
  }
  // public ProductItem(ProductPrices item) {
  //   super();
  //   // HBox container = new HBox();
  //   Product p = item.product;
  //   this.getStyleClass().add("product-item");
  //   this.setSpacing(15);
  //   this.title = new Label(p.getName());
  //   this.title.getStyleClass().add("heading-block");
  //   this.title.setWrapText(true);
  //   this.title.setPrefWidth();
  //   Image n = new Image(p.getImage());
  //   this.image = new ImageView(n);
  //   this.image.getStyleClass().add("product-image");
  //   this.image.setFitWidth(150);
  //   this.image.setFitHeight(150);
  //   this.image.setPreserveRatio(true);
  //   this.price = new Label(Float.toString(item.prices.get(item.prices.size() - 1).getPrice()));
  //   this.price.getStyleClass().add("heading-block");
  //   VBox right = new VBox();
  //   right.setSpacing(5);
  //   right.getStyleClass().add("product-content");
  //   right.getChildren().addAll(this.title, this.price);
  //   super.getChildren().addAll(this.image, right);
  //   this.productPrices = item;
  // }
  //
  // public Product getProduct() {
  //   return this.productPrices.product;
  // }
}
