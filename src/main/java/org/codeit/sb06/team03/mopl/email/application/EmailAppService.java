package org.codeit.sb06.team03.mopl.email.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.email.application.in.SendEmailCommand;
import org.codeit.sb06.team03.mopl.email.application.in.SendEmailUseCase;
import org.codeit.sb06.team03.mopl.email.domain.Email;
import org.codeit.sb06.team03.mopl.email.domain.EmailService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class EmailAppService implements SendEmailUseCase {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final EmailService emailService;

    @Override
    @Transactional
    public void sendEmail(SendEmailCommand command) {
        final String emailAddress = command.emailAddress();
        final String rawTempPassword = command.rawTempPassword();
        final Instant expireDate = command.expireDate();

        Email email = emailService.send(emailAddress, rawTempPassword, expireDate);
        // TODO : 이벤트
        // 이메일은 applicaion out이 없음
    }
}