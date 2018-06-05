package com.guerzonica.app.picodom.components.cell;


import com.guerzonica.app.picodom.components.graph.Graph;

import com.guerzonica.app.storage.models.ProductPrices;


import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;

import javafx.scene.control.ListCell;

/**
* Cell factory about graphs used by ListView
* @author Singh Amarjot
* @see com.guerzonica.app.picodom.pages.DashboardPage
*/
public class CellGraph extends ListCell<ProductPrices> {
  /**
  * From JavaFx reference: <br />
  * The updateItem method should not be called by developers, but it is the best method for developers to override to allow for them to customise the visuals of the cell. To clarify, developers should never call this method in their code (they should leave it up to the UI control, such as the ListView control) to call this method. However, the purpose of having the updateItem method is so that developers, when specifying custom cell factories (again, like the ListView cell factory), the updateItem method can be overridden to allow for complete customisation of the cell.
  */
  @Override
  public void updateItem(ProductPrices item, boolean empty) {

    if(!empty) {
      super.updateItem(item, empty);

      final Graph chart = new Graph(
        new CategoryAxis(),
        new NumberAxis(),
        item
      );

      chart.setPrefHeight(200);
      setGraphic(chart);

    }

    setPrefHeight(200);

  }

}
