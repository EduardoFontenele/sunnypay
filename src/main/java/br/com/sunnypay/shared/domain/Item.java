package br.com.sunnypay.shared.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record Item (
        @NotBlank(message = "{order.item.name.required}")
        @Size(max = 255, message = "{order.item.name.size}")
        String name,

        @NotNull(message = "{order.item.quantity.required}")
        @Min(value = 1, message = "{order.item.quantity.positive}")
        Integer quantity,

        @NotNull(message = "{order.item.amount.required}")
        @Min(value = 1, message = "{order.item.amount.positive}")
        Long unitAmount
) {}