package br.com.sunnypay.order.dto;

import br.com.sunnypay.order.domain.PaymentMethod;
import br.com.sunnypay.shared.domain.Customer;
import br.com.sunnypay.shared.domain.Item;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderRequest (
        @NotNull(message = "{order.customer.required}")
        @Valid Customer customer,
        @NotNull(message = "{order.payment.method.required}")
        PaymentMethod paymentMethod,

        @NotNull(message = "{order.amount.required}")
        @Min(value = 1, message = "{order.amount.positive}")
        Long amountInCents,

        @NotBlank(message = "{order.reference.required}")
        @Size(max = 100, message = "{order.reference.size}")
        String referenceId,

        @NotNull(message = "{order.items.required}")
        @Valid
        @Size(min = 1, message = "{order.items.min}")
        List<Item> items
) { }