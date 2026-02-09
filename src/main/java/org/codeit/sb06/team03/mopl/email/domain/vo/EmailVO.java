package org.codeit.sb06.team03.mopl.email.domain.vo;

public record EmailVO(
        String to,
        String subject,
        String body
        // Auth 패키지 infra in / controller BFF req
) {
}
