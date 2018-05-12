package com.guerzonica.app.pages.base;
//https://stackoverflow.com/questions/40750526/javafx-best-practice-for-navigating-between-ui-screens
import com.guerzonica.app.interfaces.ToolbarListener;
import com.guerzonica.app.components.Toolbar;
// import javafx.scene.Node;
// import javafx.scene.Parent;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import com.guerzonica.app.App;

public abstract class Page implements ToolbarListener{

  protected Stage stage;
  public Toolbar toolbar;
  public VBox root;
  public StackPane wrapper;
  private Scene scene;

  public Page(Stage stage){
    // this.stage =
    stage.setTitle("Pricetag");
    stage.setMaxHeight(800);
    stage.setMaxWidth(1000);
    stage.setMinHeight(400);
    stage.setMinWidth(600);
    this.stage = stage;
    this.wrapper = new StackPane();
    this.root = new VBox(15);
    this.root.setId("app");

    this.root.setMinWidth(stage.getMinWidth());
    this.root.prefWidthProperty().bind(stage.widthProperty());
    // this.toolbar = null;


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
    if(toolbar == null) toolbar();
    else this.toolbar = toolbar;



    // this.setScene(stage.getScene());
  }


  public Stage getStage(){ return this.stage; }
  public abstract void forceLoad();

  public void onBackPressed(){
    App.pageController.pop();
  }
  public void PageWillEnter(){
    // if(App.pageController.empty()) {
    //
    // }
    // else {
    // }

    System.out.println(App.pageController.size());
  }
  public void PageWillExit(){

  }
}
