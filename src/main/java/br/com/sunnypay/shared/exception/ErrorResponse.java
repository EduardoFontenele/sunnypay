package br.com.sunnypay.shared.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp,
        @JsonIgnore
        List<ErrorInformation> errors
) {
    public record ErrorInformation(
            String field,
            Object rejectedValue,
            String message
    ) {}

    public static ErrorResponse validationError(BindingResult bindingResult) {
        var fieldErrors = bindingResult.getFieldErrors().stream()
                .map(fieldError -> new ErrorInformation(
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()
                ))
                .toList();

        return new ErrorResponse("Validation failed", HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), fieldErrors);
    }
}
