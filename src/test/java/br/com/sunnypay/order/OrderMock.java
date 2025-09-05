package br.com.sunnypay.order;

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
}
