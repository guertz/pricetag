package com.guerzonica.app.http.interfaces;

import com.guerzonica.app.http.Request;
import java.net.URL;

public interface Body /* extends Runnable */ {
    @Header(Accept = "application/xml")
    @HttpMethod("GET")
    Request request(URL request);
}
