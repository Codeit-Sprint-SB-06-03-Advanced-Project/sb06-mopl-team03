package org.codeit.sb06.team03.mopl.account.infra.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.codeit.sb06.team03.mopl.account.application.in.UpdatePasswordCommand;
import org.hibernate.validator.constraints.Length;

public record PasswordUpdateRequest(

        @Schema(description = "새 비밀번호")
        @NotBlank
        @Length(min = 8)
        String newPassword

) {

}
