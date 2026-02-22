package org.codeit.sb06.team03.mopl.playlist.infra.in;

import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.common.error.ErrorResponse;
import org.codeit.sb06.team03.mopl.playlist.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@Slf4j
@RestControllerAdvice(basePackageClasses = PlaylistController.class)
public class PlaylistControllerAdvice {

    @ExceptionHandler(PlaylistNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlaylistNotFoundException(PlaylistNotFoundException e) {
        log.error(e.getMessage());
        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "Playlist를 찾을 수 없습니다.",
                Collections.emptyList()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CurationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCurationNotFoundException(CurationNotFoundException e) {
        log.error(e.getMessage());
        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "플레이리스트에 담지 않은 컨텐츠입니다.",
                Collections.emptyList()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ContentAlreadyBeenCuratedException.class)
    public ResponseEntity<ErrorResponse> handleContentAlreadyBeenCuratedException(ContentAlreadyBeenCuratedException e) {
        log.error(e.getMessage());
        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "이미 플레이리스트에 담은 컨텐츠입니다.",
                Collections.emptyList()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(SubscriptionAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionAlreadyExistsException(SubscriptionAlreadyExistsException e) {
        log.error(e.getMessage());
        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "이미 구독 중인 플레이리스트입니다.",
                Collections.emptyList()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionNotFoundException(SubscriptionNotFoundException e) {
        log.error(e.getMessage());
        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "구독 정보가 없습니다.",
                Collections.emptyList()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SelfSubscriptionNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleSelfSubscriptionNotAllowedException(SelfSubscriptionNotAllowedException e) {
        log.error(e.getMessage());
        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "본인이 만든 플레이리스트는 구독할 수 없습니다.",
                Collections.emptyList()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
