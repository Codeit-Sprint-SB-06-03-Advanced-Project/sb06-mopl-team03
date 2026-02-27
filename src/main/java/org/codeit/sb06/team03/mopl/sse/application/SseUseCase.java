package org.codeit.sb06.team03.mopl.sse.application;

import java.util.Map;
import java.util.UUID;

public interface SseUseCase {

    void send(Object data, String eventName, UUID receiverId);

    void sendAll(Map<UUID, Object> data, String eventName);

    void broadcast(String eventName, Object data);
}
