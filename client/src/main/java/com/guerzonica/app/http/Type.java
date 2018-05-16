package com.guerzonica.app.http;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
public @interface Type{
  String value() default "GET";
}
