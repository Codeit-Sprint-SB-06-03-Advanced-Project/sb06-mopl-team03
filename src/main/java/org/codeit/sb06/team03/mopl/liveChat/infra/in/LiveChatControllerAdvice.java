package org.codeit.sb06.team03.mopl.liveChat.infra.in;

import org.codeit.sb06.team03.mopl.common.error.ErrorResponse;
import org.codeit.sb06.team03.mopl.liveChat.domain.exception.LiveChatDuplicateException;
import org.codeit.sb06.team03.mopl.liveChat.domain.exception.LiveChatNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class LiveChatControllerAdvice {

    @ExceptionHandler(value = LiveChatNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLiveChatNotFoundException(LiveChatNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getSimpleName(), e.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(value = LiveChatDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleLiveChatDuplicateException(LiveChatDuplicateException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getSimpleName(), e.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
