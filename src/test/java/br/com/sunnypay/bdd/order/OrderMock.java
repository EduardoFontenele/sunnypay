package br.com.sunnypay.bdd.order;

import br.com.sunnypay.order.dto.OrderRequest;
import br.com.sunnypay.order.domain.PaymentMethod;
import br.com.sunnypay.shared.domain.Customer;
import br.com.sunnypay.shared.domain.Item;
import br.com.sunnypay.shared.domain.Phone;

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
                new Customer(
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
                new Customer(
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
                new Customer(
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
                new Customer(
                        "John Doe",
                        "john@example.com",
                        "12345678901",
                        List.of(new Phone(
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
                List.of(new Item(
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
                new Customer(
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
                new Customer(
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
                List.of(new Item(
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

    private static Customer validCustomer() {
        return new Customer(
                "John Doe",
                "john@example.com",
                "12345678901",
                List.of(validPhone())
        );
    }

    private static Phone validPhone() {
        return new Phone(
                "+55",
                "11",
                "999999999",
                Phone.PhoneType.MOBILE
        );
    }

    private static Item validItem() {
        return new Item(
                "Product A",
                1,
                1000L
        );
    }
}