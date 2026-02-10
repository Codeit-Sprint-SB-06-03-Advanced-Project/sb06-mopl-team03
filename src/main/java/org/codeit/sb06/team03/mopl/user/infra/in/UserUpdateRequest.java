package org.codeit.sb06.team03.mopl.user.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
        @Schema(description = "이름")
        @NotBlank
        String name
) {
}
