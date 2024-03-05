package com.artemnizhnyk.incognitochatapp.api.service;

import com.artemnizhnyk.incognitochatapp.api.domain.Participant;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.stream.Stream;

public class ParticipantServiceImpl implements ParticipantService {

    @Override
    public void handleJoinChat(final String sessionId, final String participantId, final String chatId) {

    }

    @Override
    public void handleUnsubscribe(final SessionUnsubscribeEvent event) {

    }

    @Override
    public void handleDisconnect(final SessionDisconnectEvent event) {

    }

    @Override
    public void handleLeaveChat(final AbstractSubProtocolEvent event) {

    }

    @Override
    public Stream<Participant> getParticipants(final String chatId) {
        return null;
    }
}
