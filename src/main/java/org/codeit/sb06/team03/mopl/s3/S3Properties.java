package org.codeit.sb06.team03.mopl.s3;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "mopl.storage.aws-s3")
public record S3Properties(
        @NotBlank String region,
        @NotBlank String bucketName,
        @NotBlank String accessKey,
        @NotBlank String secretKey,
        @NotNull @Positive Integer signatureDurationInHours
) {
}
