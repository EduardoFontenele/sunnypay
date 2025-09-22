package br.com.sunnypay.shared.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class BusinessLogicException extends RuntimeException {
    private final String reason;
    private final HttpStatus httpStatus;
}
