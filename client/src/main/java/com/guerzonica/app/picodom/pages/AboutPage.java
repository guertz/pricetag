package com.guerzonica.app.picodom.pages;

import com.guerzonica.app.picodom.pages.base.DomPage;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class AboutPage extends DomPage<HBox, VBox, HBox> {
  public static String cssName = "about-page";
  public static String title = "About";
  public AboutPage(Stage stage){
    super(stage, new HBox(), new VBox(30), new HBox(10), cssName);
    header();
    body();
    footer();

  }

  @Override
  public void header(){
    super.header();
    super.toolbar.setTitle(AboutPage.title);
    HBox ref = super.getHeader();
    Label title  = new Label("PriceTag - Amazon price tracker");
    title.getStyleClass().add("subheading");
    ref.getChildren().add(title);
  }

  @Override
  public void body(){
    super.body();
    VBox ref = super.getBody();
    ref.getStyleClass().add("center");
    ref.setFillWidth(true);
    Text t = new Text();
    t.getStyleClass().addAll("p", "text-center");
    t.setText("Pricetag is developed with Java client-side and NodeJS server side\nDevelopers: Singh Amarjot, Matteo Guerzoni");
    t.wrappingWidthProperty().bind(super.scene.widthProperty().subtract(2));
    ref.getChildren().add(t);

    Image image = new Image("icons/flowchart.png");

    // improve performance
    ImageView iv2 = new ImageView();
    iv2.setImage(image);
    iv2.setPreserveRatio(true);
    iv2.setSmooth(true);
    iv2.setCache(true);
    // iv2.setFitWidth(550);
    iv2.setFitWidth(super.getStage().getMinWidth() - 10);
    ref.getChildren().add(iv2);
    // ref.getStyleClass().add("primary");


  }

  @Override
  public void footer(){
    super.footer();
    HBox ref = super.getFooter();
    Label title  = new Label("License MIT | 2018");

    Label why  = new Label("An University project - UNIMORE");
    why.getStyleClass().add("color-white");
    ref.getChildren().addAll(title, why);
  }
}
