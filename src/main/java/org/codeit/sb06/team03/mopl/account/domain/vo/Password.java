package org.codeit.sb06.team03.mopl.account.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.codeit.sb06.team03.mopl.account.domain.exception.InvalidPasswordException;

@Embeddable
public record Password(
        @NotNull
        @Column(name = "password", nullable = false)
        String value
) {

    private static final int MIN_LENGTH = 8;

    public Password {
        if (value.length() < MIN_LENGTH) {
            throw new InvalidPasswordException(MIN_LENGTH);
        }
    }
}
