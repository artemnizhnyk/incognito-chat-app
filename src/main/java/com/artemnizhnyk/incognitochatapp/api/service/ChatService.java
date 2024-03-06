package com.artemnizhnyk.incognitochatapp.api.service;

import com.artemnizhnyk.incognitochatapp.api.domain.model.Chat;

import java.util.UUID;
import java.util.stream.Stream;

public interface ChatService {

    void createChat(final String chatName);
    void deleteChat(final UUID id);
    Stream<Chat> getChats();
}
