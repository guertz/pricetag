package com.guerzonica.app.interfaces;
// import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.control.Control;
public interface Templatable<H, B, F> {
  public B getBody();
  public H getHeader();
  public F getFooter();
  public Control getRoot();
}
