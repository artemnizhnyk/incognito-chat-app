package com.artemnizhnyk.incognitochatapp.api.service;

import com.artemnizhnyk.incognitochatapp.api.domain.model.Chat;
import com.artemnizhnyk.incognitochatapp.api.web.dto.mapper.ChatMapper;
import com.artemnizhnyk.incognitochatapp.api.web.websocket.ChatWsController;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Log4j2
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatMapper chatMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final SetOperations<String, Chat> setOperations;

    private static final String KEY = "may:code:crazy-chat:chats";

    @Override
    public void createChat(final String chatName) {
        log.info(String.format("Chat \"%s\" created.", chatName));

        Chat chat = Chat.builder()
                .name(chatName)
                .build();

        setOperations.add(KEY, chat);

        messagingTemplate.convertAndSend(
                ChatWsController.FETCH_CREATE_CHAT_EVENT,
                chatMapper.toDto(chat)
        );
    }

    @Override
    public void deleteChat(final UUID id) {
        getChats()
                .filter(chat -> Objects.equals(id, chat.getId()))
                .findAny()
                .ifPresent(chat -> {

                    log.info(String.format("Chat \"%s\" deleted.", chat.getName()));

                    setOperations.remove(KEY, chat);

                    messagingTemplate.convertAndSend(
                            ChatWsController.FETCH_DELETE_CHAT_EVENT,
                            chatMapper.toDto(chat)
                    );
                });
    }

    @Override
    public Stream<Chat> getChats() {
        return Optional
                .ofNullable(setOperations.members(KEY))
                .orElseGet(HashSet::new)
                .stream();
    }
}
