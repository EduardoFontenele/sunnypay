package br.com.sunnypay.bdd.order;

import br.com.sunnypay.order.OrderRequest;

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
