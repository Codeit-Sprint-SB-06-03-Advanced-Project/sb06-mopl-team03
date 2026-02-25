package org.codeit.sb06.team03.mopl.dm.conversation.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class ConversationEvent {

    @Getter
    @RequiredArgsConstructor
    public static final class ConversationCreatedEvent extends ConversationEvent {
        private final UUID conversationId;
        private final UUID userId;
        private final UUID withUserId;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class LiveMessageJoinedEvent extends ConversationEvent {
        private final UUID conversationId;
        private final UUID userId;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class LiveMessageLeavedEvent extends ConversationEvent {
        private final UUID conversationId;
        private final UUID userId;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class MessageReadedEvent extends ConversationEvent {
        private final UUID conversationId;
        private final UUID userId;
    }
}
