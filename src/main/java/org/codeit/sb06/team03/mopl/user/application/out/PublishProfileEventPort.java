package org.codeit.sb06.team03.mopl.user.application.out;

import org.codeit.sb06.team03.mopl.user.domain.event.UserEvent;

public interface PublishProfileEventPort {

    void publish(UserEvent userEvent);
}
