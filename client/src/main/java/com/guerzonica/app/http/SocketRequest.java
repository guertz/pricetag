package com.guerzonica.app.http;

import java.net.URISyntaxException;
import com.guerzonica.app.channel.exceptions.StreamException;
import com.guerzonica.app.channel.Channel;
import com.guerzonica.app.channel.interfaces.MessageHandler;

import com.guerzonica.app.http.interfaces.*;
  /** A type of request between sockets.
  * Same as his superclass, this class handle a communication of type Socket
  * @author Singh Amarjot
  * @see com.guerzonica.app.http.Request
  */
public class SocketRequest extends Request<String> {
    private String route;
    /**
    * Given a route, it will bind to that route.
    * @param route a string that represent route, in this case typically we have "broadcast:details" or "details"
    * @see com.guerzonica.app.storage.ProductsProvider
    * @see com.guerzonica.app.http.Request
    * @see com.guerzonica.app.http.HttpClient
    */
    public SocketRequest(String route){
      super(String.class);
      this.route = route;
    }
    /**
    * Start the thread with some preventive preparation.
    * @param listener callback
    */
    public void start(RequestHandler listener){
      this.setListener(listener);
      this.start();
    }
    /**
    * Since we have a socket there is no need to INTERRUPT
    * @see Channel
    */
    @Override
    public void run (){
      System.out.println("Binding socket on " + route);
      try{
        Channel channel = Channel.getChannel();
        channel.bindRoute(this.route, new MessageHandler() {
          @Override
          public void handle(String response) {

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
