package com.guerzonica.app.picodom.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class ImageButton extends Button {

  private void makeGraphic(String src, Number width, Number height) {
    final ImageView icon = new ImageView(new Image(src));
      icon.setFitWidth(width.doubleValue());
      icon.setFitHeight(width.doubleValue());
    
    setGraphic(icon);
    setShape(new Circle(width.doubleValue()));
    getStyleClass().addAll("fab", "primary");
  }

  public ImageButton(String src) {
    super();
    makeGraphic(src, 25, 25);
  }

  public ImageButton(String src, double width, double height){
    super();
    makeGraphic(src, width, height);
  }
  
}
