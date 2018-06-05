package com.guerzonica.app.http.interfaces;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
  /**
  * A decorator, that will handles informations about the Header of a request
  * @author Singh Amarjot
  */
@Retention(RetentionPolicy.RUNTIME)
public @interface Header{
  String Content_Type() default "application/x-www-form-urlencoded";
  String Accept() default "application/json";
}
