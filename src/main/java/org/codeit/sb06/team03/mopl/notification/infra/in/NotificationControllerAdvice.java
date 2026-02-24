package org.codeit.sb06.team03.mopl.notification.infra.in;

import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.common.error.ErrorResponse;
import org.codeit.sb06.team03.mopl.notification.domain.exception.NotificationAccessDeniedException;
import org.codeit.sb06.team03.mopl.notification.domain.exception.NotificationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@Slf4j
@RestControllerAdvice(basePackageClasses = NotificationControllerAdvice.class)
public class NotificationControllerAdvice {

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotificationNotFoundException(NotificationNotFoundException e) {
        log.error(e.getMessage());
        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "알림을 찾을 수 없습니다.",
                Collections.emptyList()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(NotificationAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleNotificationAccessDeniedException(NotificationAccessDeniedException e) {
        log.error(e.getMessage());
        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "알림 접근 권한이 없습니다.",
                Collections.emptyList()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
