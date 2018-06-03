package com.guerzonica.app.http;

import java.net.URISyntaxException;
import com.guerzonica.app.channel.exceptions.StreamException;
import com.guerzonica.app.channel.Channel;
import com.guerzonica.app.channel.interfaces.MessageHandler;

import com.guerzonica.app.http.interfaces.*;

public class SocketRequest extends Request<String> {
    private String route;
    public SocketRequest(String route){
      super(String.class);
      this.route = route;
    }
    public void start(RequestHandler listener){
      this.setListener(listener);
      this.start();
    }
    @Override
    public void run (){
      System.out.println("RUNNING SOCKET REQUEST");
      try{
        Channel channel = Channel.getChannel();
        channel.bindRoute(this.route, new MessageHandler() {
          @Override
          public void handle(String response) {
            // System.out.println("i got somethign");

            System.out.println(response);

            SocketRequest.this.listener.handle(response);
             // since is a socket there is no need to INTERRUPT. this cause problems
            // interrupt();
          }
        });
      } catch(StreamException e){
        e.printStackTrace();
      } catch(URISyntaxException e){
        e.printStackTrace();
      }
    }
}
