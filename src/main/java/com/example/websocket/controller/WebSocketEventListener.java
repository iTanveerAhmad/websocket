package com.example.websocket.controller;

import com.example.websocket.config.ChatMessage;
import com.example.websocket.config.WebSocketConfiguration;
import com.example.websocket.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketConfiguration.class);

    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    @EventListener
    public void handleWebSocketConnectListener(final SessionConnectEvent event){
        LOGGER.info("Ting Ting! we have new cheeky little connection here...");
    }

    @EventListener
    public void handleWebSocketDisconnectEvent(final SessionDisconnectEvent event){

        final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        final String username = (String) headerAccessor.getSessionAttributes().get("username");
        final ChatMessage chatMessage = ChatMessage.builder()
                .type(MessageType.DISCONNECT)
                .sender(username)
                .build();

        sendingOperations.convertAndSend("/topic/public", chatMessage);
    }

}
