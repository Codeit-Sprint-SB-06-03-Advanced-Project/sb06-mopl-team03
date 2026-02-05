package org.codeit.sb06.team03.mopl.user.infra.in;

import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.account.domain.exception.AccountRegistrationFailedException;
import org.codeit.sb06.team03.mopl.account.domain.exception.EmailAddressAlreadyExistsException;
import org.codeit.sb06.team03.mopl.account.domain.exception.InvalidEmailAddressException;
import org.codeit.sb06.team03.mopl.account.domain.exception.InvalidPasswordException;
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
}
