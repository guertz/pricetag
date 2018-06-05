package com.guerzonica.app.picodom.pages.base;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import com.guerzonica.app.picodom.components.toolbar.Toolbar;
import com.guerzonica.app.picodom.interfaces.Templatable;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
/**
* This subclass of Page provide a nice way to implement a Page like a dom document: with a Header, Body and Footer.
* This class along with {@link com.guerzonica.app.picodom.pages.base.ListPage }
* help better his subclasses to implement business logic and leave the view everything to it
* @author Singh Amarjot
*
* @see com.guerzonica.app.picodom.pages.base.ListPage
* @see com.guerzonica.app.picodom.pages.base.Page
* @see com.guerzonica.app.picodom.pages.AboutPage
*/
public class DomPage<H extends Pane, B extends Pane, F extends Pane> extends Page implements Templatable<H,B,F>{

  protected Scene scene;

  protected Pane root;

  protected H header;

  protected B body;

  protected F footer;

  private Stage stage;

  /**
  * Put everything in a BorderPane wrappend in a ScrollPane and notify the superclass to set the scene.
  * Also set predifined styles of each section, header, body, footer, so the child class will simply call those references.
  *
  * @param stage The current stage
  * @param h The header content
  * @param b The body content
  * @param f The footer content
  * @param classString The css referenced class 
  *
  * @see com.guerzonica.app.picodom.pages.base.DomPage#header To set the predefined header style.
  * @see com.guerzonica.app.picodom.pages.base.DomPage#body To set the predefined body style.
  * @see com.guerzonica.app.picodom.pages.base.DomPage#footer To set the predefined footer style.
  */
  public DomPage(Stage stage, H h, B b, F f, String classString){
    super(stage);
    this.stage = stage;
    this.body = b;
    this.header = h;
    this.footer = f;
    this.init(stage, h, b, f, classString);
  }

  private void init(Stage stage, H h, B b, F f, String classString){

    BorderPane container = new BorderPane();

    container.setTop(this.header);
    container.setCenter(this.body);
    container.setBottom(this.footer);

    this.root = new Pane();
    this.root.getChildren().add(container);


    ScrollPane scrollPane = new ScrollPane();
      scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
      scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
      scrollPane.setFitToWidth(true);
      scrollPane.setPadding(new Insets(0,0,0,0));
      scrollPane.setContent(this.root);

    container.prefWidthProperty().bind(scrollPane.widthProperty());
    body.prefWidthProperty().bind(container.widthProperty());

    this.scene = new Scene(scrollPane);
    container.prefHeightProperty().bind(this.scene.heightProperty());
    this.scene.getRoot().getStyleClass().add(classString);
    this.scene.getStylesheets().add("css/pricetheme.css");

    super.setScene(this.scene);


  }
  public void show(){
    super.show();
  }
  /**
  * @see com.guerzonica.app.picodom.interfaces.Templatable
  */
  public Pane getRoot(){ return super.root; }
  /**
  * @see com.guerzonica.app.picodom.interfaces.Templatable
  */
  public H getHeader(){ return this.header;}
  /**
  * @see com.guerzonica.app.picodom.interfaces.Templatable
  */
  public B getBody(){ return this.body; }
  /**
  * @see com.guerzonica.app.picodom.interfaces.Templatable
  */
  public F getFooter(){ return this.footer; }
  /**
  * Set predefined style of the header
  */
  public void header(){
    this.header.setPadding(new Insets(10, 10, 10, 10));
    this.header.getStyleClass().add("header");
    this.header.prefWidthProperty().bind(this.stage.widthProperty());
  }
  /**
  * Set predefined style of the body
  */
  public void body(){

    this.body.setPadding(new Insets(5, 5, 5, 5));
    this.body.getStyleClass().add("body");

   }
  public void setToolbar(Toolbar toolbar){
    super.setToolbar(toolbar);
  }
  /**
  * Set predefined style of the footer
  */
  public void footer(){
    this.footer.setPadding(new Insets(10, 10, 10, 10));
    this.footer.getStyleClass().addAll("footer");
    // this.footer.setPrefHeight(75);
    this.footer.prefWidthProperty().bind(this.stage.widthProperty());
  }
  
  public void forceLoad(){ header(); body(); footer(); }
}
