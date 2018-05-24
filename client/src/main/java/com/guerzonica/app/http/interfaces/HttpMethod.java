package com.guerzonica.app.http.interfaces;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
public @interface HttpMethod {
  String value() default "GET";
}
