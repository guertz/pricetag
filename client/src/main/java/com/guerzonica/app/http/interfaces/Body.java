package com.guerzonica.app.http.interfaces;

import com.guerzonica.app.http.SocketRequest;
import com.guerzonica.app.http.Request;
import java.net.URL;
/**
* An interface with methods used to make calls to external services.
* Decorators are very important here. This interface is built  at runtime, to prepare a request.
* @author Singh Amarjot
* @see com.guerzonica.app.http.HttpClient;
*/
public interface Body {
    /**
    * A typically HTTP request
    * @param URL an url to make a request
    * @return a thread that will return the result thanks to a callback-system through listeners.
    * @see com.guerzonica.app.http.Request;
    */
    @Header(Accept = "application/xml")
    @HttpMethod("GET")
    Request<String> request(URL request);
    /**
    * Binding to a route to listen data, with sockets.
    * @param route a route to bind to
    * @see com.guerzonica.app.http.SocketRequest
    */
    @HttpMethod("SOCKET")
    SocketRequest bindRoute(String route);
}
