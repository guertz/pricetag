package com.guerzonica.app.components;
//https://stackoverflow.com/questions/40750526/javafx-best-practice-for-navigating-between-ui-screens
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

public class DashboardPage extends Page{
  private Scene scene;
  private BorderPane root;
  public DashboardPage(Stage stage){
    super(stage);
    this.root = new BorderPane();
    root.setTop(header());
    root.setCenter(body());
    this.scene = new Scene(root);
    this.scene.getStylesheets().add("css/pricetheme.css");
    super.stage.setScene(this.scene);
  }
  public void show(){
    super.show();
  }
  public GridPane body(){
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(5, 5, 5, 5));
    grid.getStyleClass().add("body");
    // Text title = new Text("Charts");
    // title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
    // title.setTextAlignment(TextAlignment.CENTER);
    // grid.add(title, 1, 0);
    return grid;
  }
  public HBox header() {
    HBox row = new HBox();
    row.setPadding(new Insets(10, 10, 10, 10));
    row.setSpacing(10);
    row.getStyleClass().add("header");
    //set style
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

}
