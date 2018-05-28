package com.guerzonica.app.picodom.components;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import javafx.geometry.Pos;

public class SearchField extends HBox {

  private ImageButton search;
  private TextField field;

  public SearchField(String placeholder) {
    super(2);
    this.field = new TextField();
    this.field.setPromptText(placeholder);
    this.field.setMinWidth(200);
    this.field.getStyleClass().addAll("text-field", "white", "round", "dropshadow");
    this.setAlignment(Pos.CENTER);

    this.search = new ImageButton("icons/search.png");
    this.getChildren().addAll(this.field, this.search);
    // this.container.getChildren().addAll(this, this.search);
  }

  public Button getActionButton() {
    return this.search;
  }

  public String getContent() {
    return this.field.getText();
  }

}
