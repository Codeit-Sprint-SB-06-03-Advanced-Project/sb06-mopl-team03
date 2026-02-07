package org.codeit.sb06.team03.mopl.email.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.email.domain.policy.EmailSenderPolicy;
import org.codeit.sb06.team03.mopl.email.domain.vo.EmailVO;
import org.springframework.data.domain.AbstractAggregateRoot;

import static org.codeit.sb06.team03.mopl.email.domain.event.EmailEvent.EmailSentEvent;

@RequiredArgsConstructor
@Getter
public class Email extends AbstractAggregateRoot<Email> {

    private EmailVO emailVO;

    public Email(EmailVO emailVO) {
        this.emailVO = emailVO;
    }

    public void sendTo(
            EmailSenderPolicy emailSenderPolicy
    ) {
        emailSenderPolicy.send(this.emailVO);
        super.registerEvent(new EmailSentEvent());
    }
}
