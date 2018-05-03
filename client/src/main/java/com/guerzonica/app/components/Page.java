package com.guerzonica.app.components;
//https://stackoverflow.com/questions/40750526/javafx-best-practice-for-navigating-between-ui-screens
import javafx.stage.Stage;
public class Page{
  protected Stage stage;
  public Page(Stage stage){
    this.stage = stage;
  }
  public void show(){
    this.stage.show();
  }
}
