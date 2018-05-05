package com.guerzonica.app.pages;
//https://stackoverflow.com/questions/40750526/javafx-best-practice-for-navigating-between-ui-screens
import com.guerzonica.app.interfaces.Templatable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
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

public class DashboardPage extends DomPage {
  // private Scene scene;
  public DashboardPage(Stage stage){
    super(stage);
    //super.root = new BorderPane(); for now the class automatically wrap the body in a ScrollPane.
    // BorderPane container = new BorderPane();
    // container.setTop(header());
    // container.setCenter(body());
    // this.scene = new Scene(container);
    // this.scene.getStylesheets().add("css/pricetheme.css");
    // super.setScene(this.scene);
  }
  // public void show(){
  //   super.show();
  // }
  // public <? extends Node> getBody(){ return super.body; }
  //
  // public BorderPane getRoot(){ return this.root; }

  // private GridPane body(){
  //   body = new GridPane();
  //   body.setHgap(10);
  //   body.setVgap(10);
  //   body.setPadding(new Insets(5, 5, 5, 5));
  //   body.getStyleClass().add("body");
  //   Text title = new Text("Charts");
  //   title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
  //   title.setTextAlignment(TextAlignment.CENTER);
  //   this.body.add(title, 1, 0);
  //   return this.body;
  // }
  // private HBox header() {
  //   HBox row = new HBox();
  //   row.setPadding(new Insets(10, 10, 10, 10));
  //   row.setSpacing(10);
  //   row.getStyleClass().add("header");
  //   //set style
  //   final TextField search = new TextField();
  //   search.setPromptText("Insert Product ID");
  //   search.setMinWidth(300);
  //   search.getStyleClass().addAll("text-field", "flat");
  //   final Button trackButton = new Button("Track");
  //   trackButton.setMinWidth(100);
  //   trackButton.getStyleClass().addAll("button", "flat", "padding-both");
  //   row.getChildren().addAll(search, trackButton);
  //
  //   return row;
  // }

}
