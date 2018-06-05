package com.guerzonica.app.picodom.pages.base;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.input.MouseEvent;

  /**
  * A generic class that permit to built a list with a specific view and specific dataset.
  * It is like DomPage, but just another type of concept. Why i don't use DomPage? Because i want some indenpendecy from that class
  *
  * @author Singh Amarjot
  *
  * @see com.guerzonica.app.picodom.pages.base.DomPage
  * @see com.guerzonica.app.picodom.pages.base.Page
  * @see com.guerzonica.app.picodom.pages.DashboardPage
  * @see com.guerzonica.app.picodom.pages.ProductsPage
  */
public class ListPage<T> extends Page {

  protected Scene scene;
  protected ListView<T> list;

  public ListPage(Stage stage, Class<? extends ListCell<T>> blocks){
    super(stage);

    this.list = new ListView<T>();

    scene = new Scene(this.list);
      scene.getStylesheets().add("css/pricetheme.css");

    // this.scene.widthProperty().subtract(20)
    // this.scene.heightProperty()

    this.list.setCellFactory(new Callback<ListView<T>,
      ListCell<T>>() {
          @Override
          public ListCell<T> call(ListView<T> list) {

            ListCell<T> item = new ListCell<T>();

            try {
              item = blocks.newInstance();
            } catch(Exception e) { e.printStackTrace();}

            return item;
          }
      }
    );

    this.list.setOnMouseClicked(e -> onEvent(this.list.getSelectionModel().getSelectedItem()));

    this.list.prefHeightProperty().bind(scene.heightProperty());

    super.setScene(this.scene);

  }

  public void onEvent(T element){

  }


  public void unselectable(){
    this.list.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                event.consume();
            }
        });
  }
  @Override
  public void forceLoad(){}


}
