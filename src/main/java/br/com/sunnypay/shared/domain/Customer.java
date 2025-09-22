package br.com.sunnypay.shared.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record Customer(
        @NotBlank(message = "{order.customer.name.required}")
        @Size(max = 255, message = "{order.customer.name.size}")
        String name,

        @NotBlank(message = "{order.customer.email.required}")
        @Email(message = "{order.customer.email.invalid}")
        @Size(max = 255, message = "{order.customer.email.size}")
        String email,

        @NotBlank(message = "{order.customer.document.required}")
        @Pattern(regexp = "\\d{11}|\\d{14}", message = "{order.customer.document.invalid}")
        String document,

        @NotNull(message = "{order.customer.phones.required}")
        @Valid
        @Size(min = 1, max = 5, message = "{order.customer.phones.size}")
        List<Phone> phones
) {}
