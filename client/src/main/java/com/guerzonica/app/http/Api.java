package com.guerzonica.app.http;

import com.guerzonica.app.http.Request;
import java.net.URL;

public interface Api /* extends Runnable */ {
 @Header(Accept = "application/xml")
 @Type("GET")
 Request<String> request(URL request);
}
