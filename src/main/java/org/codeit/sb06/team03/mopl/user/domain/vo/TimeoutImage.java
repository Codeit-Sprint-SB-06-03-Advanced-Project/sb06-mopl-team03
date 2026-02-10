package org.codeit.sb06.team03.mopl.user.domain.vo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.codeit.sb06.team03.mopl.user.infra.out.PresignedUrlUpdateListener;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Setter
@Getter
@ToString
@Entity
@EntityListeners(PresignedUrlUpdateListener.class)
@Table(name = "timeout_images")
public class TimeoutImage {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "image_key")
    private String key;

    @Column(name = "exp")
    private Instant exp;

    @Column(name = "presigned_url", length = 1024)
    private String presignedUrl;

    public static TimeoutImage create(String key, Instant exp, String presignedUrl) {
        var timeoutImage = new TimeoutImage();
        timeoutImage.id = UUID.randomUUID();
        timeoutImage.key = key;
        timeoutImage.exp = exp;
        timeoutImage.presignedUrl = presignedUrl;
        return timeoutImage;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(exp);
    }
}
