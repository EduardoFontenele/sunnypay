package br.com.sunnypay.order.dto;

import br.com.sunnypay.order.domain.OrderStatus;
import br.com.sunnypay.shared.domain.PixPaymentLinks;

import java.time.LocalDateTime;

public record PixOrderResponse (
        String referenceId,
        OrderStatus status,
        PixPaymentLinks paymentLinks
) implements OrderResponse {

    @Override
    public String getReferenceId() {
        return referenceId;
    }

    @Override
    public OrderStatus getStatus() {
        return status;
    }

}
