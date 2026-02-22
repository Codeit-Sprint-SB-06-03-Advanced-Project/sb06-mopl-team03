package org.codeit.sb06.team03.mopl.dm.conversation.infra.out;

import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.Conversation;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.QConversation;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.Message;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.ConversationDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.DMUserDto;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.DirectMessageDto;

import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository extends QuerydslJpaRepository<Conversation, UUID> {

    default Optional<ConversationDto> findByWith(UUID withUserId) {
        QConversation conversation = QConversation.conversation;

        Conversation result = select(conversation)
                .from(conversation)
                .where(conversation.with.userId.eq(withUserId))
                .fetchFirst();

        if (result == null) {
            return Optional.empty();
        }

        return Optional.of(toConversationDto(result));
    }

    private ConversationDto toConversationDto(Conversation conversation) {
        DMUser with = conversation.getWith();
        DMUserDto withDto = new DMUserDto(
                with.userId().toString(),
                with.name(),
                with.profileImageUrl()
        );

        DirectMessageDto lastestMessageDto = null;
        Message message = conversation.getLastestMessage();
        if (message != null) {
            lastestMessageDto = new DirectMessageDto(
                    null,
                    conversation.getId().toString(),
                    message.createdAt().toString(),
                    toDMUserDto(message.sender()),
                    toDMUserDto(message.receiver()),
                    message.content()
            );
        }

        return new ConversationDto(
                conversation.getId().toString(),
                withDto,
                lastestMessageDto,
                conversation.isHasUnread()
        );
    }

    private DMUserDto toDMUserDto(DMUser dmUser) {
        return new DMUserDto(
                dmUser.userId().toString(),
                dmUser.name(),
                dmUser.profileImageUrl()
        );
    }
}
