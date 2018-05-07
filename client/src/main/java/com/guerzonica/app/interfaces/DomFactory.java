package com.guerzonica.app.interfaces;
import com.guerzonica.app.pages.DomPage;;
@Deprecated
public interface DomFactory<H,B,F>{
  public <T extends DomPage> T build(H h, B b, F f);
}
