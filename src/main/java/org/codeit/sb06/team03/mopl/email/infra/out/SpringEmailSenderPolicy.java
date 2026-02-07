package org.codeit.sb06.team03.mopl.email.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.email.domain.policy.EmailSenderPolicy;
import org.codeit.sb06.team03.mopl.email.domain.vo.EmailVO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SpringEmailSenderPolicy implements EmailSenderPolicy {

    private final JavaMailSender javaMailSender;

    @Override
    public void send(EmailVO emailVO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailVO.to());
        message.setSubject(emailVO.subject());
        message.setText(emailVO.body());

        javaMailSender.send(message);
    }
}