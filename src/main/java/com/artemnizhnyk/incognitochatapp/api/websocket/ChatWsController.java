package com.artemnizhnyk.incognitochatapp.api.websocket;

import com.artemnizhnyk.incognitochatapp.api.domain.Chat;
import com.artemnizhnyk.incognitochatapp.api.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatWsController {

    private final SimpMessagingTemplate messagingTemplate;
    public static final String FETCH_CREATE_CREATE_CHAT = "/topic/chat.{chat_name}create";
    public static final String CREATE_CHAT = "/topic.chat.create.event";

    @MessageMapping(FETCH_CREATE_CREATE_CHAT)
    public void createChat(@DestinationVariable("chat_name") final String chatName) {

        Chat chat = Chat.builder()
                .name(chatName)
                .build();

        // TODO: 17.01.2024 save in redis

        messagingTemplate.convertAndSend(
                CREATE_CHAT,
                ChatDto.builder()
                        .id(chat.getId())
                        .name(chat.getName())
                        .createdAt(chat.getCreatedAt())
                        .build()
        );
    }

    @SubscribeMapping(CREATE_CHAT)
    public ChatDto fetchCreateEvent() {
        return null;
    }

}
