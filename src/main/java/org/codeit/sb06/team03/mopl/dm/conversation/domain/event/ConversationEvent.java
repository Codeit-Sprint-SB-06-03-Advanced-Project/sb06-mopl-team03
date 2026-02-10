package org.codeit.sb06.team03.mopl.dm.conversation.domain.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class ConversationEvent {

    public static final class ConversationConnectedEvent extends ConversationEvent {}

    public static final class MessageReadEvent extends ConversationEvent {}

    public static final class LiveMessageJoinedEvent extends ConversationEvent {}
    
    public static final class LiveMessageLeavedEvent extends ConversationEvent {}

    public static final class LiveMessageStatsChangedEvent extends ConversationEvent {}
}
