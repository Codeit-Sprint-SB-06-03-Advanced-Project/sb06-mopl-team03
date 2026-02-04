package org.codeit.sb06.team03.mopl.user.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserCreateRequest(
        @Schema(description = "이름")
        @NotBlank
        String name,

        @Schema(description = "이메일")
        @NotNull
        @Email
        String email,

        @Schema(description = "비밀번호")
        @NotBlank
        @Length(min = 8)
        String password
) {
}
