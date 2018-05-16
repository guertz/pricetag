package com.guerzonica.app.http;

import com.guerzonica.app.http.Type;
import java.util.HashMap;
import com.guerzonica.app.http.Header;
import java.util.Map;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;

public class HttpClient implements InvocationHandler {

    // Request request;
    // Class<?> returnType;
    // String requestType;
    // Map<String,String> headers;
    // URL url;

    @SuppressWarnings("unchecked")
    public <T> T makeClient(Class<T> api){

      return  (T) Proxy.newProxyInstance(api.getClassLoader(), new Class[]{api}, this);
    }

    @SuppressWarnings("rawtypes")
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable, UnsupportedOperationException {
      //for now i consider ONLY GET METHODS. with a url as arg.
      // Annotation[] annotations = m.getDeclaredAnnotations();
      Request result = new Request(m.getReturnType());

      Type type = m.getAnnotation(Type.class);

      if(type.value().equals("GET")) {
        result.setRequestType(type.value());
        Header header = m.getAnnotation(Header.class);
        System.out.println(header);
        if(header != null){
          Map<String,String> headers = new HashMap<String,String>();
          headers.put("Content-Type", header.Content_Type());
          headers.put("Accept", header.Accept());

          result.setHeaders(headers);
        }

        if(args[0] instanceof String){
          result.setUrl(new URL(args[0].toString()));
        } else result.setUrl((URL)(args[0]));

      } else  throw new UnsupportedOperationException("ONLY GET REQUEST ARE AVAILABLE FOR NOW ");
      return result;
  }
}