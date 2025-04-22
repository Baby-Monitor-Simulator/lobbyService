package com.example.BabyMonitorLobbyService.WebSocket;

import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSessionManager {
    private static final ConcurrentHashMap<String, String> sessionUserMap = new ConcurrentHashMap<>();

    public static void addSession(String sessionId, String userId) {
        sessionUserMap.put(sessionId, userId);
    }

    public static String getUserId(String sessionId) {
        return sessionUserMap.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        sessionUserMap.remove(sessionId);
    }
}
