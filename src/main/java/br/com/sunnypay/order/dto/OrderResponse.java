package br.com.sunnypay.order.dto;

import br.com.sunnypay.order.domain.OrderStatus;

public interface OrderResponse {
    String getReferenceId();
    OrderStatus getStatus();
}
