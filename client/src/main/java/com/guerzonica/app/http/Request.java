package com.guerzonica.app.http;

import java.net.MalformedURLException;

import java.util.Map;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.guerzonica.app.http.interfaces.*;

public class Request<T> extends Thread {
    // Request request;
    private Class<T> returnType;
    private String requestType;
    private Map<String,String> headers;
    private URL url;

    private RequestHandler listener;

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
    public void run (){
      StringBuffer response = new StringBuffer();

      try {
        HttpURLConnection con = (HttpURLConnection) this.url.openConnection();
        con.setRequestMethod(this.requestType);
        for (Map.Entry<String, String> entry : this.headers.entrySet()){
          // System.out.println(entry.getKey() + "/" + entry.getValue());
          con.setRequestProperty(entry.getKey(), entry.getValue());
        }

        int response_code = con.getResponseCode();
        System.out.println(response_code);

    		BufferedReader in = new BufferedReader(
    		        new InputStreamReader(con.getInputStream()));
    		String inputLine;;

    		while ((inputLine = in.readLine()) != null) {
    			response.append(inputLine);
    		}

        in.close();
      } catch(IOException e){

      }

  		//print result
      listener.handle(response.toString());
      this.interrupt();
    }

    public void start(RequestHandler listener){
      this.setListener(listener);
      super.start();
    }

    public void setListener(RequestHandler listener){
      this.listener = listener;
    }

}
