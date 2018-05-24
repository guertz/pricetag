package com.guerzonica.app.http.interfaces;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
public @interface Header{
  String Content_Type() default "application/x-www-form-urlencoded";
  String Accept() default "application/json";
}
