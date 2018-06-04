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

@ClientEndpoint
public class Channel {

    private Session userSession = null;
    private Map<String, MessageHandler> bindings =
                new HashMap<String, MessageHandler>();

    private static Channel instance = null;

    public static Channel getChannel() throws URISyntaxException {
        if(instance == null)
            instance = new Channel(new URI(Env.WSLocator));

        return instance;
    }

    private Channel(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
      Container      b = Container.fromJson(message);
      MessageHandler h = bindings.get(b.getUri());

      if(h != null)
        h.handle(message);

    }

    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    // Channel.of("/path").subscribe()
    public void bindRoute(String route, MessageHandler handler) throws StreamException {
        if(bindings.get(route) != null)
            throw new StreamException(route);

        bindings.put(route, handler);
    }
}
