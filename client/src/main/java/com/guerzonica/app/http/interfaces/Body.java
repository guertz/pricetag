package com.guerzonica.app.http.interfaces;

import com.guerzonica.app.http.SocketRequest;
import com.guerzonica.app.http.Request;
import java.net.URL;

public interface Body {
    @Header(Accept = "application/xml")
    @HttpMethod("GET")
    Request<String> request(URL request);

    @HttpMethod("SOCKET")
    SocketRequest bindRoute(String route);
}
