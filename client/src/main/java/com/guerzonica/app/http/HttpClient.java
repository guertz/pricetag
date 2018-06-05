package com.guerzonica.app.http;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import com.guerzonica.app.http.interfaces.*;
/**
 * Represent an Httpclient.
 * In particular it implements at runtime, through a Proxy class, the interface {@link com.guerzonica.app.http.interfaces.Body }<br />
 * This approach is usually used to make an Http client like this, because it is usable: you have only one file to care about, the interface with your apis.
 * Through decorators you can get every information to build a request properly.
 * @author Singh Amarjot
 *
 * @see com.guerzonica.app.http.interfaces.Body
 * @see com.guerzonica.app.http.Request
 */
public class HttpClient implements InvocationHandler {
    /**
    * Returns an new type of class of Proxy class.
    * Proxy provides static methods for creating dynamic proxy classes and instances
    * @param  api the instance type Class of the interface with all methods that will be implemented at runtime
    * @return a new type of Proxy class
    */
    @SuppressWarnings("unchecked")
    public <T> T makeClient(Class<T> api){
      return  (T) Proxy.newProxyInstance(api.getClassLoader(), new Class[]{api}, this);
    }
    /**
    * The method is invoked when a call of any methods of the interface is made.
    * This method belongs the InvocationHandler interface and here all Decorators of the interface {@link com.guerzonica.app.http.interfaces.Body }
    * will be processed and a result will be return.
    * @param proxy the proxy instance that the method was invoked on.
    * @param m  the Method instance corresponding to the interface method invoked on the proxy instance.
    * @param args the arguments of the particular method
    * @return a {@link com.guerzonica.app.http.Request}
    * @throws UnsupportedOperationException if the {@link com.guerzonica.app.http.interfaces.HttpMethod} is different from GET or SOCKET
    * @throws MalformedURLException
    * @see com.guerzonica.app.http.SocketRequest
    * @see com.guerzonica.app.http.interfaces.RequestHandler
    */
    @SuppressWarnings("all")
    public Object invoke(Object proxy, Method m, Object[] args) throws UnsupportedOperationException, MalformedURLException {

      Request result = new Request(m.getReturnType());
      HttpMethod type = m.getAnnotation(HttpMethod.class);

      switch(type.value()) {
        case "GET":
            result.setRequestType(type.value());
            Header header = m.getAnnotation(Header.class);

            if(header != null){
              Map<String,String> headers = new HashMap<String,String>();

                headers.put("Content-Type", header.Content_Type());
                headers.put("Accept", header.Accept());

              result.setHeaders(headers);
            }

            if(args[0] instanceof String)
              result.setUrl(new URL(args[0].toString()));
            else
              result.setUrl((URL)(args[0]));
          break;
       case "SOCKET":
            result = new SocketRequest(args[0].toString());
          break;
        default:
          throw new UnsupportedOperationException("ONLY GET REQUEST OR SOCKETS ARE AVAILABLE FOR NOW ");
      }

      return result;
  }
}
