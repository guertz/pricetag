package com.guerzonica.app.picodom.pages.base;

import javafx.scene.control.ListView;

import javafx.scene.Scene;
import javafx.stage.Stage;

//can't extends DomPage because ListView isn't under superclass Pane
public class ListPage extends Page {

  protected Scene scene;
  protected ListView<?> list;
  protected ListPage.Listener listener;

  public ListPage(Stage stage, ListView<?> container){
    // ListPage(stage);
    super(stage);
    this.list = container;

    init();
  }
  public ListPage(Stage stage){
    super(stage);
  }
  public void setList(ListView<?> container){
    this.list = container;
    init();
  }
  private void init(){
    // ScrollPane scrollPane = new ScrollPane();
    //   scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    //   scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    // scrollPane.setContent(this.list);
    this.scene = new Scene(this.list);
    this.list.prefWidthProperty().bind(this.scene.widthProperty().subtract(20));
    this.list.prefHeightProperty().bind(this.scene.heightProperty());
    this.scene.getStylesheets().add("css/pricetheme.css");
    super.setScene(this.scene);

    this.list.setOnMouseClicked(e -> {
      if(listener != null)
      listener.handle(this.list.getSelectionModel().getSelectedItem());
    });
  }

  public void setListener(ListPage.Listener listener){
    this.listener = listener;
  }

  public static interface Listener<T> {
    void handle(T t);
  }

  @Override
  public void forceLoad(){ }
}
