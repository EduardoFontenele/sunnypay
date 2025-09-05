package br.com.sunnypay.order;

import java.time.LocalDateTime;

public record OrderResponse(
        String orderId,
        String referenceId,
        OrderStatus status,
        Long amountInCents,
        String currency,
        PaymentLinks paymentLinks,
        String selectedProvider,
        LocalDateTime createdAt
) {
    public enum OrderStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED
    }

    public record PaymentLinks(
            String pixQrCode,
            String pixQrCodeUrl,
            String paymentUrl
    ) {}
}
