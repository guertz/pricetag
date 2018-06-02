package com.guerzonica.app.picodom.components;


import com.guerzonica.app.picodom.components.Graph;

import com.guerzonica.app.storage.models.ProductPrices;


import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;

import javafx.scene.control.ListCell;


public class GraphCell extends ListCell<ProductPrices> {

  @Override
  public void updateItem(ProductPrices item, boolean empty) {

    if(!empty) {
      super.updateItem(item, empty);

      final Graph chart = new Graph(
        new CategoryAxis(),
        new NumberAxis(),
        item
      );
      chart.setMaxHeight(60);
      chart.setMinWidth(100);

      setGraphic(chart);

      //setPrefHeight(chart.getHeight());
      prefHeightProperty().bind(chart.heightProperty());
    }

    // chart.minWidthProperty().bind(width);

  }
  public GraphCell(){

  }

}
