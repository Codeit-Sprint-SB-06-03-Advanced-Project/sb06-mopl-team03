package org.codeit.sb06.team03.mopl.account.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.codeit.sb06.team03.mopl.account.domain.exception.InvalidEmailAddressException;

import java.util.regex.Pattern;

@Embeddable
public record EmailAddress(
        @NotNull
        @Column(name = "email_address", nullable = false, unique = true)
        String value
) {

    private static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public EmailAddress {
        if (!emailPattern.matcher(value).matches()) {
            throw new InvalidEmailAddressException(value);
        }
    }
}
