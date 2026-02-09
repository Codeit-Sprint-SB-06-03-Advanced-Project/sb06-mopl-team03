package org.codeit.sb06.team03.mopl.user.domain;

import org.codeit.sb06.team03.mopl.user.domain.event.UserEvent;
import org.codeit.sb06.team03.mopl.user.domain.event.UserEvent.UserProfileCreatedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Profile {

    private final UUID accountId;
    private short version;
    private String name;
    private final List<UserEvent> events = new ArrayList<>();

    public Profile(UUID accountId, String name) {
        this.accountId = accountId;
        this.version = 0;
        this.name = name;
    }

    public static Profile create(UUID accountId, String name) {
        Profile profile = new Profile(accountId, name);
        profile.events.add(new UserProfileCreatedEvent());
        return profile;
    }

    public List<UserEvent> events() {
        return List.copyOf(events);
    }

    public void clearEvents() {
        events.clear();
    }

    public Snapshot snapshot() {
        return new Snapshot(accountId, name, version);
    }

    public record Snapshot(UUID accountId, String name, short version) {
    }
}
