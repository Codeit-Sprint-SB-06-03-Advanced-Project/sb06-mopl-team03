package org.codeit.sb06.team03.mopl.user.application.in;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record UpdateProfileCommand(UUID accountId, String name, @Nullable MultipartFile image) {
}
