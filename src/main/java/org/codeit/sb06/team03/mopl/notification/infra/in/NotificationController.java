package org.codeit.sb06.team03.mopl.notification.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.bff.BffNotificationService;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notifications")
public class NotificationController implements NotificationApi {

    private final BffNotificationService bffNotificationService;

    @Override
    @GetMapping
    public ResponseEntity<CursorResponseNotificationDto> getNotifications(
            @ModelAttribute CursorRequestNotificationDto request,
            @AuthenticationPrincipal MoplUserDetails user
    ) {
        return ResponseEntity.ok(bffNotificationService.getNotifications(request, user.getId()));
    }

    @Override
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable String notificationId,
            @AuthenticationPrincipal MoplUserDetails user
    ) {
        bffNotificationService.deleteNotification(notificationId, user.getId());
        return ResponseEntity.noContent().build();
    }
}
