package org.codeit.sb06.team03.mopl.email.domain;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.email.domain.policy.EmailSenderPolicy;
import org.codeit.sb06.team03.mopl.email.domain.vo.EmailVO;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final EmailSenderPolicy emailSenderPolicy;

    public Email send(String emailAddress, String rawTempPassword, Instant expireDate) {
        final String subject = "임시 비밀번호 발급";
        final String body = rawTempPassword + "\n" + expireDate.toString();

        var emailVO = new EmailVO(emailAddress, subject, body);
        var email = new Email(emailVO);

        email.sendTo(emailSenderPolicy);

        return email;
    }
}
