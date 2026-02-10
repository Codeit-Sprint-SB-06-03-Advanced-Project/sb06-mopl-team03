package org.codeit.sb06.team03.mopl.user.infra.out;

import jakarta.persistence.PostLoad;
import org.codeit.sb06.team03.mopl.s3.S3Service;
import org.codeit.sb06.team03.mopl.user.domain.policy.PresignedUrlTimeoutPolicy;
import org.codeit.sb06.team03.mopl.user.domain.vo.TimeoutImage;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class PresignedUrlUpdateListener {

    private final S3Service s3Service;
    private final PresignedUrlTimeoutPolicy presignedUrlTimeoutPolicy;

    public PresignedUrlUpdateListener(
            S3Service s3Service,
            PresignedUrlTimeoutPolicy presignedUrlTimeoutPolicy
    ) {
        this.s3Service = s3Service;
        this.presignedUrlTimeoutPolicy = presignedUrlTimeoutPolicy;
    }

    @PostLoad
    public void updatePresignedUrl(TimeoutImage timeoutImage) {
        if (timeoutImage.isExpired()) {
            Instant exp = presignedUrlTimeoutPolicy.createExp(Instant.now());
            String presignedUrl = s3Service.createPresignedUrl(timeoutImage.getKey(), presignedUrlTimeoutPolicy.timeout());
            timeoutImage.setExp(exp);
            timeoutImage.setPresignedUrl(presignedUrl);
        }
    }
}
