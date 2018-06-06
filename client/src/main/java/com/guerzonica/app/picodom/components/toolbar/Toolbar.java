package com.guerzonica.app.picodom.components.toolbar;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
  /**
  * A Toolbar permits to set options on left and right. It is showed on top of every scene like in Android Activities.
  * It acts like a navbar but also a toolbar.
  * @author Singh Amarjot

  * @see com.guerzonica.app.picodom.pages.base.Page
  **/
public class Toolbar extends BorderPane{

  protected HBox left;
  protected HBox right;
  protected Label title;

  private Toolbar.Listener listener;

  public Toolbar(String title){
    this.getStyleClass().addAll("container","toolbar", "primary");
    // this.setMinHeight(100);
    this.left = new HBox();
    this.left.setSpacing(10);
    this.left.getStyleClass().add("container");
    this.left.setPickOnBounds(false);

    this.right = new HBox();
    this.right.setSpacing(10);
    this.right.getStyleClass().add("container");
    this.right.setPickOnBounds(false);

    this.title = new Label(title);
    this.title.getStyleClass().addAll("heading");

    Image image = new Image("icons/back.png");
    ImageView icon = new ImageView(image);
    icon.setFitWidth(25);
    icon.setFitHeight(25);

    Button button = new Button("", icon);
    button.getStyleClass().addAll("fab", "primary");
    button.setShape(new Circle(20));

    button.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
      // Event.fireEvent(this, new ToolbarEvent(ToolbarEvent.BACK_CLICK));
      this.listener.handle();
    });

    button.managedProperty().bind(button.visibleProperty());
    button.setVisible(false);

    this.left.getChildren().addAll(button, this.title);

    this.setLeft(left);
    this.setRight(this.right);
  }

  public void setTitle(String title){
    this.title.setText(title);
  }
  /**
  * Get left container
  * @return The left node
  */
  public HBox getLeftNode(){
    return this.left;
  }
  /**
  * Get right container
  * @return The right node
  */
  public HBox getRightNode(){
    return this.right;
  }
  /**
  * Binds width to stage width
  * @param stage The current stage
  */
  public void setFullSize(Stage stage){
    this.setMinWidth(stage.getMinWidth());
    this.prefWidthProperty().bind(stage.widthProperty());
  }
  /**
  * Back button
  **/
  public void setBackButton(){
    this.left.getChildren().get(0).setVisible(!this.left.getChildren().get(0).isVisible());
  }
  /**
  * Listener of the backbutton
  * @param listener The toolbar listener
  */
  public void setOnBackPressedListener (Toolbar.Listener listener) {
    this.listener = listener;
  }

  public static interface Listener {
    void handle();
  }

}
