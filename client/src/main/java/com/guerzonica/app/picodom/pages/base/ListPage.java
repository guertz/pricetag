package com.guerzonica.app.picodom.pages.base;

import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

//can't extends DomPage because ListView isn't under superclass Pane
public class ListPage<T> extends Page {

  protected Scene scene;
  protected ListView<T> list;

  public ListPage(Stage stage, Class<? extends ListCell<T>> blocks){
    super(stage);

    this.list = new ListView<T>() {{
      setPrefWidth(800);
      setPrefHeight(600);
    }};

    this.scene = new Scene(this.list) {{
      getStylesheets().add("css/pricetheme.css");
    }};

    // this.scene.widthProperty().subtract(20)
    // this.scene.heightProperty()
    
    this.list.setCellFactory(new Callback<ListView<T>,
      ListCell<T>>() {
          @Override
          public ListCell<T> call(ListView<T> list) {

            ListCell<T> item = new ListCell<T>();
            
            try {
              item = blocks.newInstance();
            } catch(Exception e) { }

            return item;
          }
      }
    );

    this.list.setOnMouseClicked(e -> onEvent(this.list.getSelectionModel().getSelectedItem()));
  }

  public void onEvent(T element){
    
  }

  @Override
  public void forceLoad(){ 

  }

}
