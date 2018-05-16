package com.guerzonica.app.http;

/* Maybe a class is better here.*/

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
public @interface Header{
  String Content_Type() default "application/x-www-form-urlencoded";
  String Accept() default "application/json";
  /*Specify here other types*/
}
