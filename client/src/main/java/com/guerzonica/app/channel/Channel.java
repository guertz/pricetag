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

import com.google.gson.Gson;

import com.guerzonica.app.channel.handler.*;
import com.guerzonica.app.channel.packet.*;

@ClientEndpoint
public class Channel {

    private Session userSession = null;
    private Map<String, IHandler> bindings = 
                new HashMap<String, IHandler>(); // multiple handlers?

    private static final Gson serializer = new Gson();
    private static Channel instance = null;

    public static Channel getChannel() throws URISyntaxException {
        if(instance == null)
            instance = new Channel(new URI("ws://localhost:8000/"));
        
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
        Base     b = serializer.fromJson(message, Base.class);
        IHandler h = bindings.get(b.getUri());
        
        if(h != null) 
            h.handleMessage(message);

    }

    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    // Observable/Lightweight callback interface
    public void bindRoute(String route, IHandler bind) throws StreamException {
        if(bindings.get(route) != null)
            throw new StreamException(route);

        bindings.put(route, bind);
    }
}