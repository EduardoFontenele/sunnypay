package br.com.sunnypay.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderRequest (
        @NotNull(message = "{order.customer.required}")
        @Valid CustomerDto customer,

        @NotNull(message = "{order.payment.method.required}")
        PaymentMethod paymentMethod,

        @NotNull(message = "{order.amount.required}")
        @Min(value = 1, message = "{order.amount.positive}")
        Long amountInCents,

        @NotBlank(message = "{order.reference.required}")
        String referenceId,

        @NotNull(message = "{order.items.required}")
        @Valid
        @Size(min = 1, message = "{order.items.min}")
        List<Item> items
) {
    public enum PaymentMethod {
        PIX,
        CREDIT_CARD,
        DEBIT_CARD
    }

    public record Item (
            @NotBlank(message = "{order.item.name.required}")
            String name,

            @NotNull(message = "{order.item.quantity.required}")
            @Min(value = 1, message = "{order.item.quantity.positive}")
            Integer quantity,

            @NotNull(message = "{order.item.amount.required}")
            @Min(value = 1, message = "{order.item.amount.positive}")
            Long unitAmount
    ) {}

    public record CustomerDto(
            @NotBlank(message = "{order.customer.name.required}")
            String name,

            @NotBlank(message = "{order.customer.email.required}")
            @Email(message = "{order.customer.email.invalid}")
            String email,

            @NotBlank(message = "{order.customer.document.required}")
            String document,

            @NotNull(message = "{order.customer.phones.required}")
            @Valid
            @Size(min = 1, message = "{order.customer.phones.min}")
            List<PhoneDto> phones
    ) {
        public record PhoneDto(
                @NotBlank(message = "{order.phone.country.required}")
                String country,

                @NotBlank(message = "{order.phone.area.required}")
                String area,

                @NotBlank(message = "{order.phone.number.required}")
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
    }
}