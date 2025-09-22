package br.com.sunnypay.shared.domain;

public record PixPaymentLinks(
        String pixQrCode,
        String pixQrCodeUrl,
        String paymentUrl
) {
}
