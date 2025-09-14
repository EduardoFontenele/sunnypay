package br.com.sunnypay.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

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
        @Size(max = 100, message = "{order.reference.size}")
        String referenceId,

        @NotNull(message = "{order.items.required}")
        @Valid
        @Size(min = 1, message = "{order.items.min}")
        List<Item> items
) {
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

    public record CustomerDto(
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
            List<PhoneDto> phones
    ) {
        public record PhoneDto(
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
    }
}