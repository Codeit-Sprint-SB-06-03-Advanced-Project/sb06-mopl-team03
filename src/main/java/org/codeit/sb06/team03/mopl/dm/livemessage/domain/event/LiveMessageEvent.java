package org.codeit.sb06.team03.mopl.dm.livemessage.domain.event;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class LiveMessageEvent {

    public static final class MessageSentEvent extends LiveMessageEvent {}

    public static final class MessageReceivedEvent extends LiveMessageEvent {}

    public static final class MessagePassedEvent extends LiveMessageEvent {}
}
