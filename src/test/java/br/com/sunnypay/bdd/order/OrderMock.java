package br.com.sunnypay.bdd.order;

import br.com.sunnypay.order.OrderRequest;
import br.com.sunnypay.order.PaymentMethod;

import java.util.List;

public final class OrderMock {
    private OrderMock() {}

    public static OrderRequest missingRequiredFields() {
        return new OrderRequest(
                null,
                null,
                null,
                "",
                null
        );
    }

    public static OrderRequest withNegativeAmount() {
        return new OrderRequest(
                validCustomer(),
                PaymentMethod.PIX,
                -100L,
                "REF123",
                List.of(validItem())
        );
    }

    public static OrderRequest withEmptyItems() {
        return new OrderRequest(
                validCustomer(),
                PaymentMethod.PIX,
                1000L,
                "REF123",
                List.of()
        );
    }

    public static OrderRequest withInvalidCustomer() {
        return new OrderRequest(
                new OrderRequest.CustomerDto(
                        "",
                        "",
                        "",
                        null
                ),
                PaymentMethod.PIX,
                1000L,
                "REF123",
                List.of(validItem())
        );
    }

    public static OrderRequest withInvalidEmail() {
        return new OrderRequest(
                new OrderRequest.CustomerDto(
                        "John Doe",
                        "invalid-email",
                        "12345678901",
                        List.of(validPhone())
                ),
                PaymentMethod.PIX,
                1000L,
                "REF123",
                List.of(validItem())
        );
    }

    public static OrderRequest withInvalidDocument() {
        return new OrderRequest(
                new OrderRequest.CustomerDto(
                        "John Doe",
                        "john@example.com",
                        "123",
                        List.of(validPhone())
                ),
                PaymentMethod.PIX,
                1000L,
                "REF123",
                List.of(validItem())
        );
    }

    public static OrderRequest withInvalidPhone() {
        return new OrderRequest(
                new OrderRequest.CustomerDto(
                        "John Doe",
                        "john@example.com",
                        "12345678901",
                        List.of(new OrderRequest.CustomerDto.PhoneDto(
                                "55",
                                "1",
                                "123",
                                null
                        ))
                ),
                PaymentMethod.PIX,
                1000L,
                "REF123",
                List.of(validItem())
        );
    }

    public static OrderRequest withInvalidItem() {
        return new OrderRequest(
                validCustomer(),
                PaymentMethod.PIX,
                1000L,
                "REF123",
                List.of(new OrderRequest.Item(
                        "",
                        0,
                        0L
                ))
        );
    }

    public static OrderRequest withTooLongReferenceId() {
        return new OrderRequest(
                validCustomer(),
                PaymentMethod.PIX,
                1000L,
                "A".repeat(101),
                List.of(validItem())
        );
    }

    public static OrderRequest withTooManyPhones() {
        return new OrderRequest(
                new OrderRequest.CustomerDto(
                        "John Doe",
                        "john@example.com",
                        "12345678901",
                        List.of(
                                validPhone(),
                                validPhone(),
                                validPhone(),
                                validPhone(),
                                validPhone(),
                                validPhone()
                        )
                ),
                PaymentMethod.PIX,
                1000L,
                "REF123",
                List.of(validItem())
        );
    }

    public static OrderRequest withTooLongCustomerName() {
        return new OrderRequest(
                new OrderRequest.CustomerDto(
                        "A".repeat(256),
                        "john@example.com",
                        "12345678901",
                        List.of(validPhone())
                ),
                PaymentMethod.PIX,
                1000L,
                "REF123",
                List.of(validItem())
        );
    }

    public static OrderRequest withTooLongItemName() {
        return new OrderRequest(
                validCustomer(),
                PaymentMethod.PIX,
                1000L,
                "REF123",
                List.of(new OrderRequest.Item(
                        "A".repeat(256),
                        1,
                        1000L
                ))
        );
    }

    public static OrderRequest validOrder() {
        return new OrderRequest(
                validCustomer(),
                PaymentMethod.PIX,
                1000L,
                "REF123",
                List.of(validItem())
        );
    }

    private static OrderRequest.CustomerDto validCustomer() {
        return new OrderRequest.CustomerDto(
                "John Doe",
                "john@example.com",
                "12345678901",
                List.of(validPhone())
        );
    }

    private static OrderRequest.CustomerDto.PhoneDto validPhone() {
        return new OrderRequest.CustomerDto.PhoneDto(
                "+55",
                "11",
                "999999999",
                OrderRequest.CustomerDto.PhoneDto.PhoneType.MOBILE
        );
    }

    private static OrderRequest.Item validItem() {
        return new OrderRequest.Item(
                "Product A",
                1,
                1000L
        );
    }
}