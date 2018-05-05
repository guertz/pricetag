package com.guerzonica.app.pages;
//https://stackoverflow.com/questions/40750526/javafx-best-practice-for-navigating-between-ui-screens
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
public abstract class Page{

  protected Stage stage;

  // protected Control root;

  public Page(Stage stage){
    // this.stage =
    stage.setTitle("Pricetag");
    stage.setMaxHeight(800);
    stage.setMaxWidth(1000);
    stage.setMinHeight(400);
    stage.setMinWidth(600);
    this.stage = stage;

  }

  // public Node getBody(){
  //   return this.body;
  // }
  //
  // public Node getHeader(){
  //   return this.header;
  // }
  //
  // public Node getFooter(){
  //   return this.footer;
  // }
  public void setScene(Scene scene){
    // scene.setRoot(this.root);
    // this.root = scrollPane;
    // ObservableList<Node> list = this.root.getChildren();
    // list.add(scene.getRoot());
    // scene.setRoot(list.to);
    // this.root.setContent(scene.getRoot());
    // scene.setRoot(this.root);
    stage.setScene(scene);
  }
  public void show(){
    this.stage.show();
  }
}
