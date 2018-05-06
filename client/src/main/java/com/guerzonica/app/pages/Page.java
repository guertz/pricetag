package com.guerzonica.app.pages;
//https://stackoverflow.com/questions/40750526/javafx-best-practice-for-navigating-between-ui-screens
// import javafx.scene.Node;
// import javafx.scene.Parent;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;

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
  public Stage getStage(){ return this.stage; }
  public abstract void transition();
  public abstract void setScene();
  public abstract <T extends Pane> T getContent();
}
