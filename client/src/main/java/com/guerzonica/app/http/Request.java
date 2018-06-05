package com.guerzonica.app.http;

import java.net.MalformedURLException;
import java.util.Map;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import com.guerzonica.app.http.interfaces.*;
import com.guerzonica.app.picodom.components.modal.Preloader;
import javafx.application.Platform;

public class Request<T> extends Thread {
    // Request request;
    public static final int RETRIES = 3;
    private Class<T> returnType;
    private String requestType;
    private Map<String,String> headers;
    private URL url;
    private Preloader preloader;
    protected RequestHandler listener;

    public Request(Class<T> type){
      this.returnType = type;
    }

    public void setUrl(URL url){
      this.url =  url;
    }

    public void setUrl(String url) throws MalformedURLException{
      this.setUrl(new URL(url));
    }
    public void setRequestType(String requestType) {
    	this.requestType = requestType;
    }

    public void setHeaders(Map<String,String> headers){
      this.headers = headers;
    }

    @Override
    public void run () {
      StringBuffer response = new StringBuffer();
      int retry = 0;
      try {
        HttpURLConnection con;
        do {
          Thread.sleep(3500 * retry);
          con = (HttpURLConnection) this.url.openConnection();
          con.setRequestMethod(this.requestType);
          for (Map.Entry<String, String> entry : this.headers.entrySet()){
            con.setRequestProperty(entry.getKey(), entry.getValue());
          }

          switch(con.getResponseCode()){
            case HttpURLConnection.HTTP_OK:
              break;
            case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
            case HttpURLConnection.HTTP_UNAVAILABLE:
              System.out.println("Request failed, trying " + (RETRIES - retry));
              retry++;
              break;
            default:
              retry++;
            break;
          }
          System.out.println(con.getResponseCode());

        } while(con.getResponseCode() != 200 && retry < RETRIES);
          if(retry == RETRIES) response.append("");
          else {
    		      BufferedReader in = new BufferedReader(
    		          new InputStreamReader(con.getInputStream()));
    		      String inputLine;

    		      while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
    		      }
              in.close();
          }
      } catch(InterruptedException | IOException e){
        e.printStackTrace();
      }
      
      Platform.runLater(() -> {
        preloader.close();

      });
      listener.handle(response.toString());
      interrupt();
    }

    public void start(RequestHandler listener){
      this.setListener(listener);
      this.preloader = new Preloader("Loading");
      super.start();
    }

    public void setListener(RequestHandler listener){
      this.listener = listener;
    }

}
