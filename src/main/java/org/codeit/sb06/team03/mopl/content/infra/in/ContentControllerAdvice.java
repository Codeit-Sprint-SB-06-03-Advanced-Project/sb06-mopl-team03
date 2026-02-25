package org.codeit.sb06.team03.mopl.content.infra.in;

import org.codeit.sb06.team03.mopl.common.error.ErrorResponse;
import org.codeit.sb06.team03.mopl.content.domain.exception.InvalidCursorFormatException;
import org.codeit.sb06.team03.mopl.content.domain.exception.ContentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ContentControllerAdvice {

    @ExceptionHandler(InvalidCursorFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCursorFormatException(InvalidCursorFormatException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getSimpleName(), e.getMessage(), List.of());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ContentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleContentNotFoundException(ContentNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getSimpleName(), e.getMessage(), List.of());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
