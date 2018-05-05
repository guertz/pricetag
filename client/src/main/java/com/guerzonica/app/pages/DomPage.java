package com.guerzonica.app.pages;
//https://stackoverflow.com/questions/40750526/javafx-best-practice-for-navigating-between-ui-screens
import com.guerzonica.app.interfaces.Templatable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
public class DomPage extends Page implements Templatable{

  protected Scene scene;

  protected Control root;

  protected Pane body;

  protected Pane header;

  protected Pane footer;

  public DomPage(Stage stage){
    super(stage);

    BorderPane container = new BorderPane();
    this.body = body();
    this.header = header();
    container.setTop(this.header);
    container.setCenter(this.body);

    ScrollPane scrollPane = new ScrollPane();
      scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
      scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

    scrollPane.setContent(container);

    this.root = scrollPane;
    this.scene = new Scene(this.root);

    this.scene.getStylesheets().add("css/pricetheme.css");
    super.setScene(this.scene);
  }
  public void show(){
    super.show();
  }
  // public <? extends Node> getBody(){ return super.body; }
  //
  // public BorderPane getRoot(){ return this.root; }

  private GridPane body(){
    GridPane body = new GridPane();
    body.setHgap(10);
    body.setVgap(10);
    body.setPadding(new Insets(5, 5, 5, 5));
    body.getStyleClass().add("body");
    // Text title = new Text("Charts");
    // title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
    // title.setTextAlignment(TextAlignment.CENTER);
    // body.add(title, 1, 0);
    return body;
  }
  private HBox header() {
    HBox row = new HBox();
    row.setPadding(new Insets(10, 10, 10, 10));
    row.setSpacing(10);
    row.getStyleClass().add("header");
    // final TextField search = new TextField();
    // search.setPromptText("Insert Product ID");
    // search.setMinWidth(300);
    // search.getStyleClass().addAll("text-field", "flat");
    // final Button trackButton = new Button("Track");
    // trackButton.setMinWidth(100);
    // trackButton.getStyleClass().addAll("button", "flat", "padding-both");
    // row.getChildren().addAll(search, trackButton);
    return row;
  }

    public Control getRoot(){ return this.root; }
    public Pane getHeader(){ return this.header;}
    public Pane getBody(){ return this.body; }
    public Pane getFooter(){ return null; }

    // @Override
    // public void setRoot(Class<? extends Control> t){
    //     ScrollPane scrollPane = new ScrollPane();
    //     scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    //     scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    //
    //     this.root =
    // } //wrap everthing into the single root


}
