package com.guerzonica.app.components;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;

public class Toolbar extends BorderPane{
  protected HBox left;
  protected HBox right;
  // protected VBox container;
  public Toolbar(String title){
    this.getStyleClass().addAll("container","toolbar", "primary");
    this.left = new HBox();
    this.left.setSpacing(10);
    this.left.getStyleClass().add("container");
    this.left.setPickOnBounds(false);

    Label head = new Label(title);
    head.getStyleClass().addAll("bold","heading");

    Image image = new Image("icons/back.png");
    ImageView icon = new ImageView(image);
    icon.setFitWidth(25);
    icon.setFitHeight(25);

    Button button = new Button("", icon);
    button.getStyleClass().addAll("fab", "primary");
    button.setShape(new Circle(20));

    button.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {

      Event.fireEvent(this, new ToolbarEvent(ToolbarEvent.BACK_CLICK));

    });
    button.managedProperty().bind(button.visibleProperty());
    button.setVisible(false);

    this.left.getChildren().addAll(button, head);

    this.setLeft(left);
    this.setRight(right);

  }

  public void canGoBack(){

  }

  public void setBackButton(){
    this.left.getChildren().get(0).setVisible(!this.left.getChildren().get(0).isVisible());
  }
}
