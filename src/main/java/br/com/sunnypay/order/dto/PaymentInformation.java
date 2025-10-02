package br.com.sunnypay.order.dto;

import br.com.sunnypay.payment.domain.CustomerPaymentCredentials;

public record PaymentInformation (
        OrderRequest order,
        CustomerPaymentCredentials customerPaymentCredentials
) {
}
