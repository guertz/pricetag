package com.guerzonica.app.interfaces;
import com.guerzonica.app.pages.base.DomPage;;

@Deprecated
public interface DomFactory<H,B,F>{

  @SuppressWarnings("all")
  public <T extends DomPage> T build(H h, B b, F f);
}
