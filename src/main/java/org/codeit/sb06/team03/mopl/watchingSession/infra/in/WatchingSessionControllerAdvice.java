package org.codeit.sb06.team03.mopl.watchingSession.infra.in;

import org.codeit.sb06.team03.mopl.common.error.ErrorResponse;
import org.codeit.sb06.team03.mopl.watchingSession.domain.exception.WatchingSessionDuplicateException;
import org.codeit.sb06.team03.mopl.watchingSession.domain.exception.WatchingSessionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class WatchingSessionControllerAdvice {

    @ExceptionHandler(WatchingSessionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWatchingSessionNotFoundException(WatchingSessionNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getSimpleName(), e.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(WatchingSessionDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleWatchingSessionDuplicateException(WatchingSessionDuplicateException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getSimpleName(), e.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
