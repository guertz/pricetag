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
  /**
  * Takes  care of the Modal. A modal is graphic element that pop up in a nice way to give information to user.
  * @author Singh Amarjot
  */
public class Modal extends StackPane {
  /**
  * Container that will guest the modal
  * The container should be a StackPane, that is why there is a wrapper inside Page
  * @see com.guerzonica.app.picodom.pages.base.Page
  * @see com.guerzonica.app.picodom.pages.base.Page#setScene
  */
  protected Pane container;
  /**
  * Content of the Modal
  */
  protected Region content;
  /*
  * View holder. It will wrap the content.
  **/
  protected VBox holder;

  /**
  * Title
  */
  protected Label title;
  protected Stage stage;
  /**
  * A simple animation
  */
  private Timeline animation;

  /**
  * Event handler. In particular it will fire when the modal is closes
  */
  EventHandler<? super MouseEvent> closeHandler = e -> close();

  /**
  * Arguments constructor with everything
  **/
  public Modal(String title, Pane container, Region content){
    this.content = content;
    this.title = new Label(title.equals("") ? "" : title);
    this.container = container;
    play();

  }
  /**
  * Arguments constructor with only container and title.
  * The consumer of this class can decide futher how to proceed to add the content and show the Modal
  **/
  public Modal(String title, Pane container){
    this.title = new Label(title.equals("") ? "" : title);
    this.container = container;
  }

  public void setContent(Region content){
    this.content = content;
  }
  public void show(){

    this.animation.play();
  }
  /**
  * Get Timeline to make an animation
  */
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
  /**
  * Close a modal with inverted animation and fire event
  */
  public void close(){
    this.animation.setRate(-1);
    this.animation.play();
    this.animation.setOnFinished(e ->{

      Event.fireEvent(Modal.this, new ModalEvent(ModalEvent.CLOSE));
      this.removeEventHandler(MouseEvent.MOUSE_PRESSED, closeHandler);
      this.container.getChildren().remove(this);
    });

  }
  /**
  * Add the modal in the given container(usually the scene) and show the Modal
  */
  private void addToContainer(){
    this.container.getChildren().add(this);
    show();
  }
  /**
  * Initialize everything and start the modal
  */
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

    this.getChildren().add(holder);

    this.addEventHandler(MouseEvent.MOUSE_PRESSED, closeHandler);

    this.animation = this.ModalTransition();

    addToContainer();


  }
  /**
  * Set title of the header
  **/
  public void setTitle(String title){
    this.title.setText(title);
  }





}
