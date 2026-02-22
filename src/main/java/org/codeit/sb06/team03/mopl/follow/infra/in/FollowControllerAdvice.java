package org.codeit.sb06.team03.mopl.follow.infra.in;

import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.common.error.ErrorResponse;
import org.codeit.sb06.team03.mopl.follow.domain.exception.FolloweeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@Slf4j
@RestControllerAdvice(basePackageClasses = FollowController.class)
public class FollowControllerAdvice {

    @ExceptionHandler(FolloweeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFolloweeNotFoundException(FolloweeNotFoundException e) {
        log.error(e.getMessage(), e);

        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "팔로우 대상을 찾을 수 없습니다.",
                Collections.emptyList()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
