package org.codeit.sb06.team03.mopl.user.application.out;

import org.codeit.sb06.team03.mopl.user.domain.event.UserEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PublishProfileEventAdapter implements PublishProfileEventPort {

    private final ApplicationEventPublisher publisher;

    public PublishProfileEventAdapter(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(UserEvent userEvent) {
        publisher.publishEvent(userEvent);
    }
}
