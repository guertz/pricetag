package com.guerzonica.app.http;

public interface RequestListener<T>{
  void onResponse(T data);
}
