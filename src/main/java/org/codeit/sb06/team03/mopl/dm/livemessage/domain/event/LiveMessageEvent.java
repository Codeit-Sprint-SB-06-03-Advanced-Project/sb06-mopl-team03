package org.codeit.sb06.team03.mopl.dm.livemessage.domain.event;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class LiveMessageEvent {

    @Getter
    @RequiredArgsConstructor
    public static final class MessageSentEvent extends LiveMessageEvent {
        private final UUID messageId;
        private final UUID conversationId;
        private final UUID senderId;
        private final UUID receiverId;
        private final String content;
        private final Instant createdAt;
        private final DMUser sender;
        private final DMUser receiver;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class MessageReceivedEvent extends LiveMessageEvent {
        private final UUID messageId;
        private final UUID conversationId;
        private final UUID senderId;
        private final UUID receiverId;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class MessagePassedEvent extends LiveMessageEvent {
        private final UUID messageId;
        private final UUID conversationId;
        private final UUID receiverId;
        private final String content;
    }
}
