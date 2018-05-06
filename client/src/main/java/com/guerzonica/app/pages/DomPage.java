package com.guerzonica.app.pages;
//https://stackoverflow.com/questions/40750526/javafx-best-practice-for-navigating-between-ui-screens

import java.util.function.Supplier;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
// import java.util.Set;
import com.guerzonica.app.interfaces.Templatable;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.GridPane;
// import javafx.scene.layout.Region;
// import javafx.scene.control.TextField;
// import javafx.scene.text.Text;
// import javafx.scene.text.TextAlignment;
// import javafx.scene.text.FontWeight;
// import javafx.scene.text.Font;
// import javafx.scene.control.Button;
// import javafx.scene.Parent;


public class DomPage<H extends Pane, B extends Pane, F extends Pane> extends Page implements Templatable<H,B,F>{

  protected Scene scene;

  protected Control root;

  protected H header;

  protected B body;

  protected F footer;

  public DomPage(Stage stage, H h, B b, F f)
    throws InstantiationException, IllegalAccessException {
    super(stage);
    BorderPane container = new BorderPane();
    this.body = body(b);
    this.header = header(h);
    container.setTop(this.header);
    container.setCenter(this.body);

    ScrollPane scrollPane = new ScrollPane();
      scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
      scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

    scrollPane.setContent(container);

    this.root = scrollPane;
    this.scene = new Scene(this.root);

    this.scene.getStylesheets().add("css/pricetheme.css");
    // super.setScene(this.scene);
  }

  public void show(){
    super.show();
  }

  public void setScene(){
    super.setScene(this.scene);
  }

  private B body(B body)
    throws InstantiationException, IllegalAccessException {
    body.setPadding(new Insets(5, 5, 5, 5));
    body.getStyleClass().add("body");
    return body;
  }
  private H header(H h)
    throws InstantiationException, IllegalAccessException {
    h.setPadding(new Insets(10, 10, 10, 10));
    h.getStyleClass().add("header");
    return h;
  }

  public <B extends Pane> B getContent(){
    return (B) this.getBody();
  }
  public Control getRoot(){ return this.root; }

  public H getHeader(){ return this.header;}
  public void header(){  }

  public B getBody(){ return this.body; }
  public void body(){  }

  public F getFooter(){ return null; }
  public void footer(){  }

  public void transition() { /* Nothing here */ }


    // @Override
    // public void setRoot(Class<? extends Control> t){
    //     ScrollPane scrollPane = new ScrollPane();
    //     scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    //     scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    //
    //     this.root =
    // } //wrap everthing into the single root

    // public DomPage(Stage stage, Supplier<H> h, Supplier<B> b, Supplier<F> f )
    // //   // public DomPage<H extends Pane, B extends Pane, F extends Pane>(Stage stage)
    // //
    //   throws InstantiationException, IllegalAccessException {
    //   super(stage);
    //   // DomPage<H,B,F>(stage, (H) h.get(), (B) b.get(), (F)f.get());
    //
    // }
}
