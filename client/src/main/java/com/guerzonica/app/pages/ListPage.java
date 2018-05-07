package com.guerzonica.app.pages;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
public class ListPage extends Page{
  protected Scene scene;
  protected VBox list;
  public ListPage(VBox container){
    this.list = container;
    this.scene = new Scene(container);
  }
  public void add(Pane t){

  }
  public void transition() { /* Nothing here */ }

  public void forceLoad(){ }

  public void setScene(){

  }

  public <T extends Pane> T getContent(){
    return (T) new Pane();
  }

}
