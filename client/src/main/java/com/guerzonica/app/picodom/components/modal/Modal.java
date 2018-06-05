package com.guerzonica.app.picodom.components.modal;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.stage.Stage;
public class Modal extends StackPane {
  protected Pane container;
  protected Region content;
  protected VBox holder;
  protected Label title;
  protected Stage stage;
  private Timeline animation;

  private double translateY = 0;

  EventHandler<? super MouseEvent> closeHandler = e -> close();

  static {

  }
  /*The container should be a StackPane, that is why there is a wrapper inside Page. See public StackPane wrapper in @Page*/
  public Modal(String title, Pane container, Region content){
    this.content = content;
    this.title = new Label(title.equals("") ? "" : title);
    this.container = container;
    play();

  }
  public Modal(String title, Pane container){
    this.title = new Label(title.equals("") ? "" : title);
    this.container = container;
  }
  public void setContent(Region content){
    this.content = content;
  }
  public Modal(Stage stage, String title, Pane container, Region content){
    this.content = content;
    this.title = new Label(title.equals("") ? "" : title);
    this.container = container;
    this.stage = stage;
    play();
  }
  public void show(){

    this.animation.play();
  }
  public  Timeline ModalTransition(){
    // Timeline timeline  = new Timeline(

    /*new KeyFrame(Duration.ZERO,
      new KeyValue(this.holder.translateYProperty(), translateY , Interpolator.EASE_IN),
      new KeyValue(this.opacityProperty(), 0, Interpolator.EASE_BOTH)
    ),

    new KeyFrame(Duration.millis(150),
      new KeyValue(this.holder.translateYProperty(), 0 , Interpolator.EASE_IN),
      new KeyValue(this.opacityProperty(), 1, Interpolator.EASE_BOTH)
    ));*/

  Timeline timeline  = new Timeline(
        new KeyFrame(Duration.ZERO,
            new KeyValue(holder.scaleXProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(holder.scaleYProperty(), 0, Interpolator.EASE_BOTH),
            new KeyValue(Modal.this.visibleProperty(), false, Interpolator.EASE_BOTH)
        ),
        new KeyFrame(Duration.millis(10),
            new KeyValue(Modal.this.visibleProperty(), true, Interpolator.EASE_BOTH),
            new KeyValue(Modal.this.opacityProperty(), 0, Interpolator.EASE_BOTH)
        ),
        new KeyFrame(Duration.millis(250),
            new KeyValue(holder.scaleXProperty(), 1, Interpolator.EASE_BOTH),
            new KeyValue(holder.scaleYProperty(), 1, Interpolator.EASE_BOTH),
            new KeyValue(Modal.this.opacityProperty(), 1, Interpolator.EASE_BOTH)
        ));

    timeline.setDelay(Duration.ZERO);
    return timeline;
  }

  public void close(){
    this.animation.setRate(-1);
    this.animation.play();
    this.animation.setOnFinished(e ->{

      Event.fireEvent(Modal.this, new ModalEvent(ModalEvent.CLOSE));
      this.removeEventHandler(MouseEvent.MOUSE_PRESSED, closeHandler);
      this.container.getChildren().remove(this);
    });

  }

  private void addToContainer(){
    this.container.getChildren().add(this);
    show();
  }

  public void play(){
    this.prefWidthProperty().bind(container.widthProperty());
    this.prefHeightProperty().bind(container.heightProperty());
    this.getStyleClass().add("dialog-overlay");

    this.holder = new VBox();
    this.holder.getStyleClass().addAll("dialog", "padding");
    this.holder.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(2), null)));
    this.holder.setMaxSize(this.container.getWidth()  - 100, Region.USE_PREF_SIZE);
    HBox header = new HBox();
    // Label title =
    header.getChildren().add(title);
    header.getStyleClass().add("center");

    title.getStyleClass().add("title");

    StackPane.setAlignment(this, Pos.CENTER);

    holder.getChildren().addAll(header, content);

    this.translateY = this.container.getHeight();

    this.getChildren().add(holder);

    this.addEventHandler(MouseEvent.MOUSE_PRESSED, closeHandler);

    this.animation = this.ModalTransition();

    addToContainer();


  }

  public void setTitle(String title){
    this.title.setText(title);
  }





}
