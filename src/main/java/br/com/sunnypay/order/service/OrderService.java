package br.com.sunnypay.order.service;

import br.com.sunnypay.order.dto.OrderResponse;
import br.com.sunnypay.order.dto.OrderRequest;
import br.com.sunnypay.payment.domain.CustomerPaymentCredentials;

public interface OrderService {
    OrderResponse process(OrderRequest orderRequest, CustomerPaymentCredentials paymentCredentials);
}
