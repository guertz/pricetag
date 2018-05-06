package com.guerzonica.app.interfaces;

import javafx.scene.control.Control;
public interface Templatable<H, B, F> {
  public void header();
  public H getHeader();

  public void body();
  public B getBody();

  public void footer();
  public F getFooter();

  public Control getRoot();
}
