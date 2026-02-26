package org.codeit.sb06.team03.mopl.dm.conversation.infra.in;

import org.codeit.sb06.team03.mopl.dm.conversation.domain.exception.ConversationAlreadyExistsException;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.exception.ConversationNotFoundException;
import org.codeit.sb06.team03.mopl.dm.conversation.domain.exception.LiveMessageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "org.codeit.sb06.team03.mopl.dm")
public class DMControllerAdvice {

    @ExceptionHandler(ConversationNotFoundException.class)
    public ResponseEntity<String> handleConversationNotFound(ConversationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(LiveMessageNotFoundException.class)
    public ResponseEntity<String> handleLiveMessageNotFound(LiveMessageNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ConversationAlreadyExistsException.class)
    public ResponseEntity<String> handleConflict(ConversationAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
