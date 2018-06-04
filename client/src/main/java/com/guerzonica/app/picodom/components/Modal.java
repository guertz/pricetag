package com.guerzonica.app.picodom.components;

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
  protected String title;
  protected Stage stage;
  private Timeline animation;

  private double translateY = 0;

  EventHandler<? super MouseEvent> closeHandler = e -> close();

  static {

  }
  /*The container should be a StackPane, that is why there is a wrapper inside Page. See public StackPane wrapper in @Page*/
  public Modal(String title, Pane container, Region content){
    this.content = content;
    this.title = title;
    this.container = container;
    init();
    addToContainer();
  }
  public Modal(Stage stage, String title, Pane container, Region content){
    this.content = content;
    this.title = title;
    this.container = container;
    this.stage = stage;
    init();
    this.toFront();

    addToContainer();
  }
  public  Timeline ModalTransition(){
    Timeline timeline  = new Timeline(

    new KeyFrame(Duration.ZERO,
      new KeyValue(this.holder.translateYProperty(), translateY , Interpolator.EASE_BOTH),
      new KeyValue(this.opacityProperty(), 0, Interpolator.EASE_BOTH)
    ),

    new KeyFrame(Duration.millis(450),
      new KeyValue(this.holder.translateYProperty(), 0 , Interpolator.EASE_BOTH),
      new KeyValue(this.opacityProperty(), 1, Interpolator.EASE_BOTH)
    ));

    timeline.setDelay(Duration.ZERO);
    return timeline;
  }
  private void addToContainer(){

    this.container.getChildren().add(this);
    show();
  }
  public void show(){

    this.animation.play();
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
  private void init(){
    this.prefWidthProperty().bind(container.widthProperty());
    this.prefHeightProperty().bind(container.heightProperty());
    this.getStyleClass().add("dialog-overlay");

    this.holder = new VBox();
    this.holder.getStyleClass().addAll("dialog", "padding");
    this.holder.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(2), null)));
    this.holder.setMaxSize(this.container.getWidth()  - 100, Region.USE_PREF_SIZE);

    Label title = new Label("Header");
    title.setText(this.title);
    title.getStyleClass().add("title");

    StackPane.setAlignment(this, Pos.CENTER);

    holder.getChildren().addAll(title, content);

    this.translateY = this.container.getHeight();

    this.getChildren().add(holder);

    this.addEventHandler(MouseEvent.MOUSE_PRESSED, closeHandler);

    this.animation = this.ModalTransition();


  }





}
