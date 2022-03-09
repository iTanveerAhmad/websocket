package com.example.websocket.config;

import com.example.websocket.model.MessageType;
import lombok.Builder;
import lombok.Getter;


@Builder
public class ChatMessage {

    @Getter
    private MessageType type;
    @Getter
    private String content;
    @Getter
    private String sender;
    @Getter
    private String time;


}
