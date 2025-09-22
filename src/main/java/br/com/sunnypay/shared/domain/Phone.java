package br.com.sunnypay.shared.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record Phone(
        @NotBlank(message = "{order.phone.country.required}")
        @Pattern(regexp = "\\+\\d{1,3}", message = "{order.phone.country.invalid}")
        String country,

        @NotBlank(message = "{order.phone.area.required}")
        @Pattern(regexp = "\\d{2}", message = "{order.phone.area.invalid}")
        String area,

        @NotBlank(message = "{order.phone.number.required}")
        @Pattern(regexp = "\\d{8,9}", message = "{order.phone.number.invalid}")
        String number,

        @NotNull(message = "{order.phone.type.required}")
        PhoneType type
) {
    public enum PhoneType {
        BUSINESS,
        MOBILE,
        HOME
    }
}
