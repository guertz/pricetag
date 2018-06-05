package com.guerzonica.app.http;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import com.guerzonica.app.http.interfaces.*;

public class HttpClient implements InvocationHandler {
    // public static Preloader preloader;
    @SuppressWarnings("unchecked")
    public <T> T makeClient(Class<T> api){
      return  (T) Proxy.newProxyInstance(api.getClassLoader(), new Class[]{api}, this);
    }

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
