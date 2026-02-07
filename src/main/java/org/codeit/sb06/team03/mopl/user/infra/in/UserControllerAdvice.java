package org.codeit.sb06.team03.mopl.user.infra.in;

import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.account.domain.exception.*;
import org.codeit.sb06.team03.mopl.common.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@Slf4j
@RestControllerAdvice(basePackageClasses = UserController.class)
public class UserControllerAdvice {

    @ExceptionHandler(EmailAddressAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAddressAlreadyExistsException e) {
        log.error(e.getMessage());

        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "이미 사용 중인 이메일입니다",
                Collections.emptyList()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidEmailAddressException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEmailException(InvalidEmailAddressException e) {
        log.error(e.getMessage());

        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "유효하지 않은 이메일 형식입니다",
                Collections.emptyList()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException e) {
        log.error(e.getMessage());

        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "유효하지 않은 비밀번호 형식입니다",
                Collections.emptyList()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AccountRegistrationFailedException.class)
    public ResponseEntity<ErrorResponse> handleAccountRegistrationFailedException(AccountRegistrationFailedException e) {
        log.error(e.getMessage());

        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "계정 등록에 실패했습니다",
                Collections.emptyList()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidAccountIdFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAccountIdFormatException(InvalidAccountIdFormatException e) {
        log.error(e.getMessage());
        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "UUID 형식이 일치하지 않습니다.",
                Collections.emptyList()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRoleException(InvalidRoleException e) {
        log.error(e.getMessage());
        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "존재하지 않는 Role 입니다.",
                Collections.emptyList()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException e) {
        log.error(e.getMessage());
        var errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "Account를 찾을 수 없습니다.",
                Collections.emptyList()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
