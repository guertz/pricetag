package com.guerzonica.app.components;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class ImageButton extends Button{

  public ImageButton(String src){
    super();
    Image image = new Image(src);
    ImageView icon = new ImageView(image);
    icon.setFitWidth(25);
    icon.setFitHeight(25);
    this.setGraphic(icon);
    this.setShape(new Circle(20));
    this.getStyleClass().addAll("fab", "primary");
  }
  public ImageButton(String src, double width, double height){
    super();
    Image image = new Image(src);
    ImageView icon = new ImageView(image);
    icon.setFitWidth(width);
    icon.setFitHeight(height);
    this.setGraphic(icon);
    this.setShape(new Circle(width));
    this.getStyleClass().addAll("fab", "primary");
  }
}
