package org.codeit.sb06.team03.mopl.account.application.in;

import org.codeit.sb06.team03.mopl.user.infra.in.CursorRequestUserDto;
import org.codeit.sb06.team03.mopl.user.infra.in.CursorResponseUserDto;

public interface GetAccountUseCase {

    CursorResponseUserDto get(CursorRequestUserDto request);
}
