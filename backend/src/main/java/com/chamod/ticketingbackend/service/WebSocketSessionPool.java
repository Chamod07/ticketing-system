package com.chamod.ticketingbackend.service;

import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WebSocketSessionPool {
    @Getter
    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    public static void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public static void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

}