package com.guerzonica.app.picodom.components.modal;

import javafx.scene.layout.*;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import com.guerzonica.app.App;
import javafx.scene.input.MouseEvent;
  /**
  * A particular type of Modal that acts like a preloader.
  * @author Singh Amarjot
  * @see com.guerzonica.app.http.Request
  */
public class Preloader extends Modal{
  private ImageView preloader;
  /**
  * Build a simple content as a preloader
  * @param label The preloader label
  */
  public Preloader(String label){
    super(label, App.pageController.getActivePage().getWrapper());
    Image gif = new Image("icons/amazonloader.gif");
    this.preloader = new ImageView(gif);
    this.preloader.setFitWidth(220);
    this.preloader.setPreserveRatio(true);
    HBox content = new HBox();
    content.getStyleClass().add("center");
    content.getChildren().add(preloader);
    super.setContent(content);
    super.play();
    super.removeEventHandler(MouseEvent.MOUSE_PRESSED, super.closeHandler);

  }

}
