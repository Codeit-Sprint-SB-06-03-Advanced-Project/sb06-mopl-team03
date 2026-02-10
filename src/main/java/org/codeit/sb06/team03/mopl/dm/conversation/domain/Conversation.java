package org.codeit.sb06.team03.mopl.dm.conversation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Conversation {

    @Id
    private UUID id;
}
