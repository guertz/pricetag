package com.guerzonica.app.picodom.pages.base;

import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import com.guerzonica.app.App;

import com.guerzonica.app.picodom.components.Toolbar;

public abstract class Page implements Toolbar.Listener {

  protected Stage stage;
  public Toolbar toolbar;
  public VBox root;
  public StackPane wrapper;
  private Scene scene;

  public Page(Stage stage){
    
    stage.setTitle("Pricetag");
    stage.setMinHeight(400);
    stage.setMinWidth(600);
    
    stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue){
        stage.setWidth(stage.getWidth()-1);
        stage.setHeight(stage.getHeight()-1);
      }
    });

    this.stage = stage;
    this.wrapper = new StackPane();
    this.root = new VBox(15);
    this.root.setId("app");

    this.root.setMinWidth(stage.getMinWidth());
    this.root.prefWidthProperty().bind(stage.widthProperty());

    toolbar();

    this.wrapper.getChildren().add(this.root);

  }

  public void toolbar(){
    this.toolbar = new Toolbar("");
    this.toolbar.setOnBackPressedListener(this);
  }

  public Page(){

  }

  public void setScene(Scene scene){

    this.root.getChildren().add(this.toolbar);
    this.root.getChildren().add(scene.getRoot());
    scene.setRoot(this.wrapper);

    this.scene = scene;
  }

  public void show(){
    this.stage.show();
  }

  public void showPage(){
    stage.setScene(this.scene);
  }

  public void setToolbar(Toolbar toolbar){
    if(toolbar == null) 
      toolbar();
    else 
      this.toolbar = toolbar;
  }


  public Stage getStage() { 
    return this.stage; 
  }

  public abstract void forceLoad();

  public void handle(){
    App.pageController.pop();
  }

  public void PageWillEnter() {
    
  }

  public void PageWillExit() {

  }
  
}
