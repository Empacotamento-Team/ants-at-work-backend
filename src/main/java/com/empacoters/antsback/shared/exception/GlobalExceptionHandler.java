package com.empacoters.antsback.shared.exception;

import com.empacoters.antsback.shared.dto.ApiError;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException e) {
        var status = HttpStatus.NOT_FOUND;
        var apiError = new ApiError(
            status.value(),
            status.name(),
            "NAO_ENCONTRADO",
            "Recurso não encontrado: " + e.getMessage()
        );

        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ApiError> handleInvalidEmail(InvalidEmailException e) {
        var status = HttpStatus.BAD_REQUEST;
        var apiError = new ApiError(
                status.value(),
                status.name(),
                "EMAIL_INVALIDO",
                e.getMessage()
        );

        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception e) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var apiError = new ApiError(
                status.value(),
                status.name(),
                "ERRO_INESPERADO",
                "Um erro inesperado aconteceu: " + e.getMessage()
        );

        return ResponseEntity.status(status).body(apiError);
    }
}
