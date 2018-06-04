package com.guerzonica.app.picodom.components;

import com.guerzonica.app.storage.models.Product;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListCell;

public class CellProduct extends ListCell<Product>  {

  @Override
  public void updateItem(Product item, boolean empty) {
    if(!empty) {
      super.updateItem(item, empty);

      final Label title = new Label();
        title.setText(item.getName());
        title.getStyleClass().add("heading-block");
        title.setWrapText(true);

      final Label description = new Label();
        description.setText(item.getDescription());
        description.getStyleClass().add("heading-block");

      final ImageView image = new ImageView(new Image(item.getImage(), true));
        image.getStyleClass().add("product-image");
        image.setFitWidth(150);
        image.setFitHeight(150);
        image.setPreserveRatio(true);

      final VBox details = new VBox();
        details.getStyleClass().add("product-content");
        details.setSpacing(5);
        details.getChildren().addAll(title, description);

      final HBox container = new HBox();
        container.getStyleClass().add("product-item");
        container.setSpacing(15);
        container.getChildren().addAll(image, details);

      prefWidthProperty()
        .bind(getListView().widthProperty());

      setGraphic(container);
    }
  }

}
