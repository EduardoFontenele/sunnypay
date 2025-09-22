package br.com.sunnypay.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static br.com.sunnypay.shared.exception.ErrorResponse.validationError;

@RestControllerAdvice
public class ApiControllerAdvice {

    private final static String REQUIRED_BODY_MISSING = "Body is missing or have invalid values";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(validationError(ex.getBindingResult()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException ex) {
        var response = new ErrorResponse(
                REQUIRED_BODY_MISSING,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ErrorResponse> handle(BusinessLogicException e) {
        var response = new ErrorResponse(
                e.getReason(),
                e.getHttpStatus().value(),
                LocalDateTime.now(),
                null
        );

        return new ResponseEntity<>(response, e.getHttpStatus());
    }
}
