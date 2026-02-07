package org.codeit.sb06.team03.mopl.email.domain.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract sealed class EmailEvent {
    public static final class EmailRegisteredEvent extends EmailEvent {
    }

    @RequiredArgsConstructor
    public static final class EmailSentEvent extends EmailEvent {
        //todo: 채우기
    }
}
