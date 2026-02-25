package org.codeit.sb06.team03.mopl.user.application.in;

import java.util.UUID;

public record CreateProfileCommand(UUID accountId, String name) {
}
