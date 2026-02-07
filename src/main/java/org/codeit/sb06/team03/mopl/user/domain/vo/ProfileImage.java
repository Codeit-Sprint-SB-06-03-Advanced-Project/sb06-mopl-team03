package org.codeit.sb06.team03.mopl.user.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record ProfileImage(@Column(name = "profile_url") String url) {
}
