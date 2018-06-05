package com.guerzonica.app.channel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import com.guerzonica.app.channel.exceptions.*;
import com.guerzonica.app.channel.interfaces.*;
import com.guerzonica.app.channel.models.*;
import com.guerzonica.app.env.Env;

/**
 * Websocket client.
 * 
 * <br>
 * Singleton instance that handles the channel to exchange data
 * with other connected peers
 * 
 * @author Matteo Guerzoni
 */
@ClientEndpoint
public class Channel {

    /** Websocket current session */
    private Session userSession = null;

    /** Bind a callback to a specific identifier (route) */
    private Map<String, MessageHandler> bindings =
                new HashMap<String, MessageHandler>();

    /** Channel instance */
    private static Channel instance = null;

    /** 
     * Create or Return Channel instance
     * 
     * @return The Channnel instance.
     * 
     * @throws URISyntaxException If the provided websocket URI is not valid
     */
    public static Channel getChannel() throws URISyntaxException {
        if(instance == null)
            instance = new Channel(new URI(Env.WSLocator));

        return instance;
    }

    /**
    * Initialize the Channel instance.
    *
    * <br>
    * This methods takes care of connection/communication error throwing a runtime
    * exception if something goes wrong.
    *
    * @param endpointURI The given Websocket endpoint
    */
    private Channel(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Notify when the connection is completed and succesfully
     * 
     * @param userSession The current session
     */
    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    /**
     * Notify when the connection is terminated
     * 
     * @param userSession The current session
     * @param reason The "close" event
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }

    /**
     * Notify when a new message is received.
     * 
     * <br>
     * This method takes care of routing the received message
     * to the right handler specified in the bindings map.
     * 
     * @param message The received message.
     */
    @OnMessage
    public void onMessage(String message) {
      Container      b = Container.fromJson(message);
      MessageHandler h = bindings.get(b.getUri());

      if(h != null)
        h.handle(message);
    }

    /**
     * Sends a new message to the websocket server.
     * 
     * @param message The message to send
     */
    public void sendMessage(String message) { // TODO: try polymorfism Packet<...> s
        this.userSession.getAsyncRemote().sendText(message);
    }

    /**
     * Bind the specified route to the specified callback
     * 
     * @throws StreamException If the specified route is already mapped
     * 
     * @param route The route identifier
     * @param handler The MessageHandler
     */
    public void bindRoute(String route, MessageHandler handler) throws StreamException {
        if(bindings.get(route) != null)
            throw new StreamException(route);

        bindings.put(route, handler);
    }
}
