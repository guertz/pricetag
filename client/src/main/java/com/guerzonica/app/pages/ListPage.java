package com.guerzonica.app.pages;
import javafx.scene.control.ListView;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
public class ListPage extends Page{
  protected Scene scene;
  protected ListView<?> list;
  // public ListPage(Stage stage){
  //   // super(stage);
  //   super(stage);
  // }
  public ListPage(Stage stage, ListView<?> container){
    // ListPage(stage);
    super(stage);
    this.list = container;

    init();
  }
  public ListPage(ListView<?> container){
    this.list = container;
    init();
  }
  private void init(){
    ScrollPane scrollPane = new ScrollPane();
      scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
      scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    scrollPane.setContent(this.list);
    this.scene = new Scene(scrollPane);

    this.scene.getStylesheets().add("css/pricetheme.css");

    super.setScene(this.scene);


  }


  @Override
  public void forceLoad(){ }



}
