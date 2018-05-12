package com.guerzonica.app.interfaces;

import com.guerzonica.app.components.ProductItem;
public interface ItemSelectedListener<T>{
  void onItemSelect(T t);
}
