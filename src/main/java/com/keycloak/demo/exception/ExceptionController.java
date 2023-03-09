package com.keycloak.demo.exception;

import com.keycloak.demo.base.ErrorMessage;
import com.keycloak.demo.base.util.TimezoneUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController {
    protected ResponseEntity<?> exceptionHandler(HttpStatus httpStatus, String uri, String message) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .statusCode(httpStatus.value())
                .date(LocalDateTime.now(TimezoneUtil.getZoneIdJakarta()))
                .message(message)
                .uri(uri)
                .build();

        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMessage);
    }
    @ExceptionHandler(AuthenticationServiceException.class)
    ResponseEntity<?> runtimeExceptionHandler(RuntimeException ex, WebRequest request){
        return exceptionHandler(HttpStatus.UNAUTHORIZED, request.getDescription(false), ex.getMessage());
    }
}
