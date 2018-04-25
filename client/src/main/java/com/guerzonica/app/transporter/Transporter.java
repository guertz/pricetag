package com.guerzonica.app.transporter;

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

@ClientEndpoint
public class Transporter {

    private Session userSession = null;

    private Map<String, MessageHandler> handlers = new HashMap<String, MessageHandler>();

    private static Transporter instance = null;

    public static Transporter getTransporter() throws URISyntaxException {
        if(instance == null)
            instance = new Transporter(new URI("ws://localhost:8000/"));
        
        return instance;
    }

    private Transporter(URI endpointURI) {
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
        System.out.println(message);
        // match key and invoke
        // use lamba fn
        // single channel instance
    }

    private void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    public void streamFromRequest(String action, MessageHandler msgHandler) throws StreamException {
        if(handlers.get(action) != null)
            throw new StreamException(action);

        handlers.put(action, msgHandler);
            sendMessage(action);
    }

    public static interface MessageHandler {
        public void handleMessage(String message);
    }
}