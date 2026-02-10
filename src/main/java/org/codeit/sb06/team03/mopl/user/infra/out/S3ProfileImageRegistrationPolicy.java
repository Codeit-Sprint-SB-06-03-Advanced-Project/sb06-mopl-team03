package org.codeit.sb06.team03.mopl.user.infra.out;

import org.codeit.sb06.team03.mopl.s3.S3Service;
import org.codeit.sb06.team03.mopl.user.domain.exception.ImageRegistrationFailedException;
import org.codeit.sb06.team03.mopl.user.domain.policy.PresignedUrlTimeoutPolicy;
import org.codeit.sb06.team03.mopl.user.domain.policy.ProfileImageKeyGenerationPolicy;
import org.codeit.sb06.team03.mopl.user.domain.policy.ProfileImageRegistrationPolicy;
import org.codeit.sb06.team03.mopl.user.domain.vo.TimeoutImage;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;

@Component
public class S3ProfileImageRegistrationPolicy implements ProfileImageRegistrationPolicy {

    private final S3Service s3Service;
    private final ProfileImageKeyGenerationPolicy profileImageKeyGenerationPolicy;
    private final PresignedUrlTimeoutPolicy presignedUrlTimeoutPolicy;

    public S3ProfileImageRegistrationPolicy(
            S3Service s3Service,
            ProfileImageKeyGenerationPolicy profileImageKeyGenerationPolicy,
            PresignedUrlTimeoutPolicy presignedUrlTimeoutPolicy
    ) {
        this.s3Service = s3Service;
        this.profileImageKeyGenerationPolicy = profileImageKeyGenerationPolicy;
        this.presignedUrlTimeoutPolicy = presignedUrlTimeoutPolicy;
    }

    @Override
    public TimeoutImage register(MultipartFile image) {
        try {
            String key = profileImageKeyGenerationPolicy.generate();
            s3Service.uploadFile(key, image);
            Instant exp = presignedUrlTimeoutPolicy.createExp(Instant.now());
            String presignedUrl = s3Service.createPresignedUrl(key, presignedUrlTimeoutPolicy.timeout());
            return TimeoutImage.create(key, exp, presignedUrl);
        } catch (IOException e) {
            throw new ImageRegistrationFailedException(image, e);
        }
    }
}
