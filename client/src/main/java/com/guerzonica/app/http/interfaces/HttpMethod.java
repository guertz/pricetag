package com.guerzonica.app.http.interfaces;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
/**
* A decorator, that will handles informations about the type of request
* @author Singh Amarjot
*/
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpMethod {
  String value() default "GET";
}
