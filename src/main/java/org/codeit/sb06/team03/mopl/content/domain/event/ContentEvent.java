package org.codeit.sb06.team03.mopl.content.domain.event;

public abstract sealed class ContentEvent {

    private static final class ContentCreatedEvent extends ContentEvent {
    }

    private static final class ContentUpdatedEvent extends ContentEvent {
    }

    private static final class ContentDeletedEvent extends ContentEvent {
    }

    private static final class ReviewCreatedEvent extends ContentEvent {
    }

    private static final class ReviewUpdatedEvent extends ContentEvent {
    }

    private static final class ReviewDeletedEvent extends ContentEvent {
    }
}
