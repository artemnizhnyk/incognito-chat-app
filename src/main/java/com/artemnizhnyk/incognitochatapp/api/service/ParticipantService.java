package com.artemnizhnyk.incognitochatapp.api.service;

import com.artemnizhnyk.incognitochatapp.api.domain.Participant;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.UUID;
import java.util.stream.Stream;

public interface ParticipantService {

    void handleJoinChat(String sessionId, UUID participantId, String chatId);

    void handleUnsubscribe(SessionUnsubscribeEvent event);

    void handleDisconnect(SessionDisconnectEvent event);

    void handleLeaveChat(AbstractSubProtocolEvent event);

    Stream<Participant> getParticipants(String chatId);
}
