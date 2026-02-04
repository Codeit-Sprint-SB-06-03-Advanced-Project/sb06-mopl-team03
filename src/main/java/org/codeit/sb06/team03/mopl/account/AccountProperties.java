package org.codeit.sb06.team03.mopl.account;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "mopl.account")
public record AccountProperties(@NotBlank String passwordPolicy) {
}
