package br.com.sunnypay.order;

import java.time.LocalDateTime;

public record PixOrderResponse(
        String orderId,
        String referenceId,
        OrderStatus status,
        Long amountInCents,
        String currency,
        PaymentLinks paymentLinks,
        String selectedProvider,
        LocalDateTime createdAt
) {
    public record PaymentLinks(
            String pixQrCode,
            String pixQrCodeUrl,
            String paymentUrl
    ) {}
}
