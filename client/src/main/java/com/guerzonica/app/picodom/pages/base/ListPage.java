package com.guerzonica.app.picodom.pages.base;

import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
//can't extends DomPage because ListView isn't under superclass Pane
public class ListPage<T,V extends ListCell<T>> extends Page {

  protected Scene scene;
  protected ListView<T> list;
  protected V view;
  protected ListPage.Listener listener;

  public ListPage(Stage stage,  ObservableList<T> data, V view){
    super(stage);
    this.list = new ListView<T>(data);
    this.view = view;
    init();
  }
  public ListPage(Stage stage){
    super(stage);
  }
  public void setList(ListView<T> container){
    this.list = container;
    init();
  }

  @SuppressWarnings("unchecked")
  private void init(){

    this.scene = new Scene(this.list);
    this.list.prefWidthProperty().bind(this.scene.widthProperty().subtract(20));
    this.list.prefHeightProperty().bind(this.scene.heightProperty());
    this.scene.getStylesheets().add("css/pricetheme.css");
    super.setScene(this.scene);

    this.list.setCellFactory(new Callback<ListView<T>,
      ListCell<T>>() {
          @Override
          public ListCell<T> call(ListView<T> list) {
            ListCell<T> instance = new ListCell<T>();

            try {
              instance = view.getClass().newInstance();
            } catch(InstantiationException e){ System.err.println("Can't instantiate the provided class."); }
              catch(IllegalAccessException e){ System.err.println("Can't instantiate the provided class."); }

            return instance;
          }
      }
  );
    this.list.setOnMouseClicked(e -> {
      if(listener != null)
        listener.handle(this.list.getSelectionModel().getSelectedItem());
    });
  }

  public void setListener(ListPage.Listener<T> listener){
    this.listener = listener;
  }

  public static interface Listener<T> {
    void handle(T t);
  }

  @Override
  public void forceLoad(){ }
}
