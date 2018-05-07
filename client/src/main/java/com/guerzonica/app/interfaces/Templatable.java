package com.guerzonica.app.interfaces;

import javafx.scene.layout.Pane;
public interface Templatable<H, B, F> {
  public void header();
  public H getHeader();

  public void body();
  public B getBody();

  public void footer();
  public F getFooter();

  public Pane getRoot();
}
