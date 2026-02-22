package org.codeit.sb06.team03.mopl.dm.livemessage.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.UUID;

@Entity
public class LiveMessage extends AbstractAggregateRoot<LiveMessage> {

    @Id
    private UUID id;
}
