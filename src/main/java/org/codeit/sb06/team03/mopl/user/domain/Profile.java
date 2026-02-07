package org.codeit.sb06.team03.mopl.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codeit.sb06.team03.mopl.user.domain.vo.ProfileImage;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "version", nullable = false)
    private short version;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private ProfileImage image;
}
