package org.codeit.sb06.team03.mopl.notification.domain;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationService {

    public Notification create(UUID receiverId, String title, String content, NotificationLevel level) {
        return Notification.create(receiverId, title, content, level);
    }

}
