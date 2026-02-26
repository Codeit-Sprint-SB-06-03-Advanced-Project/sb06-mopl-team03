package org.codeit.sb06.team03.mopl.dm.livemessage.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.dm.conversation.application.in.GetDMUserUseCase;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.vo.DMUser;
import org.codeit.sb06.team03.mopl.dm.livemessage.application.out.LoadLiveDMUserPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class LoadLiveDMUserAdapter implements LoadLiveDMUserPort {

    private final GetDMUserUseCase getDMUserUseCase;

    @Override
    public DMUser findByUserId(UUID userId) {
        return getDMUserUseCase.findByUserId(userId);
    }
}
