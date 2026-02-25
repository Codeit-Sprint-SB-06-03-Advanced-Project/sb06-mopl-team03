package org.codeit.sb06.team03.mopl.liveChat.infra.out;

import org.codeit.sb06.team03.mopl.liveChat.domain.LiveChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LiveChatRepository extends JpaRepository<LiveChat, UUID> {

}
