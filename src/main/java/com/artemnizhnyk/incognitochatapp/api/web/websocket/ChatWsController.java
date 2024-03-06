package com.artemnizhnyk.incognitochatapp.api.web.websocket;

import com.artemnizhnyk.incognitochatapp.api.domain.model.Chat;
import com.artemnizhnyk.incognitochatapp.api.web.dto.ChatDto;
import com.artemnizhnyk.incognitochatapp.api.web.dto.MessageDto;
import com.artemnizhnyk.incognitochatapp.api.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatWsController {

    private final ParticipantService participantService;
    private final SimpMessagingTemplate messagingTemplate;

    public static final String CREATE_CHAT = "/topic/chats.create";

    public static final String FETCH_CREATE_CHAT_EVENT = "/topic/chats.create.event";
    public static final String FETCH_DELETE_CHAT_EVENT = "/topic/chats.delete.event";

    public static final String SEND_MESSAGE_TO_ALL = "/topic/chats.{chat_id}.messages.send";
    public static final String SEND_MESSAGE_TO_PARTICIPANT = "/topic/chats.{chat_id}.participants.{participant_id}.messages.send";

    public static final String FETCH_MESSAGES = "/topic/chats.{chat_id}.messages";
    public static final String FETCH_PERSONAL_MESSAGES = "/topic/chats.{chat_id}.participants.{participant_id}";

    @MessageMapping(CREATE_CHAT)
    public void createChat(final String chatName) {
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

    @SubscribeMapping(FETCH_CREATE_CHAT_EVENT)
    public ChatDto fetchCreateChatEvent() {
        return null;
    }

    @MessageMapping(SEND_MESSAGE_TO_ALL)
    public void sendMessageToAll(@DestinationVariable("chat_id") final String chatId,
                                 @Header final String simpSessionId,
                                 final String message
    ) {
        sendMessage(getFetchMessagesDestination(chatId), simpSessionId, message);
    }

    @MessageMapping(SEND_MESSAGE_TO_PARTICIPANT)
    public void sendMessageToParticipant(@DestinationVariable("chat_id") final String chatId,
                                         @DestinationVariable("participant_id") final String participantId,
                                         @Header final String simpSessionId,
                                         final String message
    ) {
        sendMessage(getFetchPersonalMessagesDestination(chatId, participantId), simpSessionId, message);
    }

    @SubscribeMapping(FETCH_MESSAGES)
    public MessageDto fetchMessages() {
        return null;
    }

    @SubscribeMapping(FETCH_PERSONAL_MESSAGES)
    public MessageDto fetchPersonalMessages(@DestinationVariable("chat_id") final String chatId,
                                            @DestinationVariable("participant_id") final String participantId,
                                            @Header final String simpSessionId
    ) {
        return null;
    }

    private void sendMessage(final String chatId, final String simpSessionId, final String message) {
        messagingTemplate.convertAndSend(
                chatId,
                MessageDto.builder()
                        .from(simpSessionId)
                        .message(message)
                        .build()
        );
    }

    private static String getFetchMessagesDestination(final String chatId) {
        return FETCH_MESSAGES.replace("{chat_id}", chatId);
    }

    private static String getFetchPersonalMessagesDestination(String chatId, String participantId) {
        return FETCH_PERSONAL_MESSAGES
                .replace("{chat_id}", chatId)
                .replace("{participant_id}", participantId);
    }
}
