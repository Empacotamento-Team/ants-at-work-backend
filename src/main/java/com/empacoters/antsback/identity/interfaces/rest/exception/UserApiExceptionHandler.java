package com.empacoters.antsback.identity.interfaces.rest.exception;

import com.empacoters.antsback.identity.application.exception.*;
import com.empacoters.antsback.shared.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserApiExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentials(InvalidCredentialsException e) {
        var status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status)
            .body(new ApiError(status.value(), status.name(),"CREDENCIAIS_INVALIDAS", e.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyRegistered(EmailAlreadyRegisteredException e) {
        var status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status)
            .body(new ApiError(status.value(), status.name(), "EMAIL_JA_CADASTRADO", e.getMessage()));
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<ApiError> handleUserDoesNotExist(UserDoesNotExistException e) {
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
            .body(new ApiError(status.value(), status.name(), "USUARIO_NAO_EXISTE", e.getMessage()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> handleInvalidToken(InvalidTokenException e) {
        var status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status)
            .body(new ApiError(status.value(), status.name(), "TOKEN_INVALIDO", e.getMessage()));
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ApiError> handleExpiredToken(ExpiredTokenException e) {
        var status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status)
                .body(new ApiError(status.value(), status.name(), "TOKEN_EXPIRADO", e.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordResetTokenException.class)
    public ResponseEntity<ApiError> handleInvalidPasswordResetToken(InvalidPasswordResetTokenException e) {
        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(new ApiError(status.value(), status.name(), "TOKEN_REDEFINICAO_INVALIDO", e.getMessage()));
    }

    @ExceptionHandler(ExpiredPasswordResetTokenException.class)
    public ResponseEntity<ApiError> handleExpiredPasswordResetToken(ExpiredPasswordResetTokenException e) {
        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(new ApiError(status.value(), status.name(), "TOKEN_REDEFINICAO_EXPIRADO", e.getMessage()));
    }
}
