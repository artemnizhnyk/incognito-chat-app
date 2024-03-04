package com.artemnizhnyk.incognitochatapp.api.websocket;

import com.artemnizhnyk.incognitochatapp.api.domain.Chat;
import com.artemnizhnyk.incognitochatapp.api.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatWsController {

    private final SimpMessagingTemplate messagingTemplate;

    public static final String FETCH_CREATE_CHAT_EVENT = "/topic/chats.create.event";
    public static final String FETCH_DELETE_CHAT_EVENT = "/topic/chats.delete.event";

    public static final String SEND_MESSAGE_TO_ALL = "/topic/chats.{chat_id}.messages.send";
    public static final String SEND_MESSAGE_TO_PARTICIPANT = "/topic/chats.{chat_id}.participants.{participant_id}.messages.send";

    public static final String FETCH_MESSAGES = "/topic/chats.{chat_id}.messages";
    public static final String FETCH_PERSONAL_MESSAGES = "/topic/chats.{chat_id}.participants.{participant_id}";

    @MessageMapping(FETCH_CREATE_CHAT_EVENT)
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
