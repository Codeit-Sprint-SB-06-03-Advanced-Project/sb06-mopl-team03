package org.codeit.sb06.team03.mopl.dm.conversation.application.in;

public interface MessageReadUseCase {

    void read(MessageReadCommand command);

    void markAsUnread(java.util.UUID conversationId, java.util.UUID receiverId);
}
