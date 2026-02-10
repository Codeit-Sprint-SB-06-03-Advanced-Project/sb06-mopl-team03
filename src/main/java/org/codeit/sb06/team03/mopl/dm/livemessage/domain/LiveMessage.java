package org.codeit.sb06.team03.mopl.dm.livemessage.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class LiveMessage {

    @Id
    private UUID id;
}
