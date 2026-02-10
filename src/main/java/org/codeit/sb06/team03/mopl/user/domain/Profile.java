package org.codeit.sb06.team03.mopl.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.user.domain.event.UserEvent.UserProfileCreatedEvent;
import org.codeit.sb06.team03.mopl.user.domain.event.UserEvent.UserProfileUpdatedEvent;
import org.codeit.sb06.team03.mopl.user.domain.policy.ProfileImageRegistrationPolicy;
import org.codeit.sb06.team03.mopl.user.domain.vo.TimeoutImage;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "profiles")
public class Profile extends AbstractAggregateRoot<Profile> {

    @Id
    @Column(name = "id")
    private UUID accountId;

    @Version
    @Column(name = "version")
    private short version;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private TimeoutImage timeoutImage;

    @MapsId("accountId")
    @JoinColumn(name = "id")
    @OneToOne
    private Account account;

    public static Profile create(UUID accountId, String name) {
        Profile profile = new Profile();
        profile.accountId = accountId;
        profile.name = name;
        profile.version = 0;
        profile.registerEvent(new UserProfileCreatedEvent());
        return profile;
    }

    public Profile update(
            String name,
            @Nullable MultipartFile image,
            ProfileImageRegistrationPolicy profileImageRegistrationPolicy
    ) {
        this.name = name;
        if (image != null && !image.isEmpty()) {
            this.timeoutImage = profileImageRegistrationPolicy.register(image);
        }
        super.registerEvent(new UserProfileUpdatedEvent());
        return this;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
