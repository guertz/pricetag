package com.guerzonica.app.picodom.pages.base;

import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import com.guerzonica.app.App;

import com.guerzonica.app.picodom.components.toolbar.Toolbar;
/**
* A Page represent a scene.
* Since the app pattern takes inspiration from Ionic & PWA web apps, here this class permits to abstract the Page or Scene concept
* In particular this class is responsible to exchange scenes, and to handle a global toolbar. All classes that extends Page will be able to
* consume his methods and will be able to manipulate the scene. This permits the code to be reusable, sinche the code to change or init or update a scene is the same.
* We don't choose to use FXML because this project is about OOP. It is more challenging to make a similary concept from scratch through classes.
*
* @author Singh Amarjot
*
* @see com.guerzonica.app.picodom.pages.base.DomPage
* @see com.guerzonica.app.picodom.pages.base.ListPage
*
*/
public abstract class Page implements Toolbar.Listener {
  /**
  * A global stage(window) of the app.
  */
  protected Stage stage;
  /**
  * root of the active scene. All the scene tree will be wrapped into the root VBOX.
  */
  protected VBox root;
  /**
  * Global wrapper that inglobe the root. This is useful when you have Modals, Popover. They will be on top and will not cause problems
  */
  protected StackPane wrapper;
  /**
  * Global toolbar,  all subclasses can perfectly manipulate this.
  * @see com.guerzonica.app.picodom.components.toolbar.Toolbar
  */
  public Toolbar toolbar;

  /**
  * Active scene
  */
  private Scene scene;
  /**
  * will prepare the window and the toolbar.
  * @param stage the primary stage of the App.
  */
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
  /**
  * New toolbar
  */
  public void toolbar(){
    this.toolbar = new Toolbar("");
    this.toolbar.setOnBackPressedListener(this);
  }

  public StackPane getWrapper(){
    return this.wrapper;
  }
  /**
  * When a subclass will set a scene, this will wrap the tree in root and then in the wrapper.
  * This will not cause problem with a fixed toolbar, modals or popover.
  */
  public void setScene(Scene scene){

    this.root.getChildren().add(this.toolbar);
    this.root.getChildren().add(scene.getRoot());
    scene.setRoot(this.wrapper);

    this.scene = scene;
  }

  public void show(){
    this.stage.show();
  }
  /**
  * Simply set the current scene with all graphics nodes.
  */
  public void showPage(){
    stage.setScene(this.scene);
  }
  /**
  * Useful when a subclass want a specific own toolbar.
  */
  public void setToolbar(Toolbar toolbar){
    if(toolbar == null)
      toolbar();
    else
      this.toolbar = toolbar;
  }

  public Stage getStage() {
    return this.stage;
  }
  /**
  * Abstract method if a subclass want to implement a forceLoad method, when a new Page is pushed into the Stack
  */
  public abstract void forceLoad();

  public void handle(){
    App.pageController.pop();
  }
  /**
  * Lifecyle method called everytime this Page is about to exit from the view.(the scene will be destroyed)
  * @see com.guerzonica.app.picodom.Navigation
  */
  public void PageWillEnter(){}
  /**
  * Lifecyle method called everytime this Page is about to enter from the view.(the scene will be visibile)
  * @see com.guerzonica.app.picodom.Navigation
  */
  public void PageWillExit(){}

}
