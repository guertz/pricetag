package com.guerzonica.app.picodom.interfaces;

import javafx.scene.layout.Pane;

/**
* Generic interface tha represent an Object with a Header, Body, Footer and obviusly a root.
* @see com.guerzonica.app.picodom.pages.base.DomPage
*/
public interface Templatable<H, B, F> {
  public void header();
  public H getHeader();

  public void body();
  public B getBody();

  public void footer();
  public F getFooter();

  public Pane getRoot();
}
