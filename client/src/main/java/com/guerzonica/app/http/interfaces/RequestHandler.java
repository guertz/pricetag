package com.guerzonica.app.http.interfaces;
/**
* Used to make a callback system to return the response from an async request
* @author Singh Amarjot
*/
public interface RequestHandler {
  void handle(String message);
}
