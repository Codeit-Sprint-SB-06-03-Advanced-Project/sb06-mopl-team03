package org.codeit.sb06.team03.mopl.email.domain.policy;

import org.codeit.sb06.team03.mopl.email.domain.vo.EmailVO;
public interface EmailSenderPolicy {

    void send(EmailVO emailVO);
}
