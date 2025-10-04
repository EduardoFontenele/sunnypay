package br.com.sunnypay.payment.service;

import br.com.sunnypay.order.dto.PaymentInformation;
import br.com.sunnypay.shared.domain.PixPaymentLinks;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PixPaymentService {

    private final PaymentClient client = new PaymentClient();

    public PixPaymentLinks process(PaymentInformation paymentInformation) {
        try {
            log.info("Iniciando processamento PIX para referência: {}", paymentInformation.order().referenceId());

            MercadoPagoConfig.setAccessToken(paymentInformation.customerPaymentCredentials().getAccessToken());

            var request = buildPixPaymentRequest(paymentInformation);

            var requestOptions = buildRequestOptions(paymentInformation.order().referenceId());

            var payment = client.create(request, requestOptions);

            log.info("Pagamento PIX criado com sucesso. ID: {}", payment.getId());

            return extractPixData(payment);

        } catch (MPException e) {
            log.error("Erro ao processar pagamento PIX: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao processar pagamento PIX: " + e.getMessage(), e);
        } catch (MPApiException e) {
            log.error(e.getApiResponse().getContent());
            throw new RuntimeException(e);
        }
    }

    private PaymentCreateRequest buildPixPaymentRequest(PaymentInformation paymentInfo) {
        var order = paymentInfo.order();
        var customer = order.customer();

        var amount = BigDecimal.valueOf(order.amountInCents())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        var nameParts = customer.name().trim().split("\\s+", 2);
        var firstName = nameParts[0];
        var lastName = nameParts.length > 1 ? nameParts[1] : "";

        log.debug("Criando request PIX teste - Valor: R$ {}, Cliente: {}", amount, customer.email());

        return PaymentCreateRequest.builder()
                .transactionAmount(amount)
                .description("")
                .paymentMethodId("pix")
                .externalReference(order.referenceId())
                .payer(PaymentPayerRequest.builder()
                        .email(customer.email())
                        .firstName(firstName)
                        .lastName(lastName)
                        .identification(IdentificationRequest.builder()
                                .type(customer.document().length() == 11 ? "CPF" : "CNPJ")
                                .number(customer.document())
                                .build())
                        .build())
                .build();
    }


    private MPRequestOptions buildRequestOptions(String referenceId) {
        var headers = new HashMap<String, String>();
        headers.put("X-Idempotency-Key", "pix-" + referenceId + "-" + System.currentTimeMillis());

        return MPRequestOptions.builder()
                .customHeaders(headers)
                .build();
    }

    private PixPaymentLinks extractPixData(Payment payment) {
        if (payment.getPointOfInteraction() == null ||
                payment.getPointOfInteraction().getTransactionData() == null) {
            log.error("Dados do PIX não encontrados na resposta do Mercado Pago");
            throw new RuntimeException("Dados do PIX não encontrados na resposta do Mercado Pago");
        }

        var transactionData = payment.getPointOfInteraction().getTransactionData();

        var qrCodeBase64 = transactionData.getQrCodeBase64();
        var qrCode = transactionData.getQrCode();
        var ticketUrl = transactionData.getTicketUrl();

        // Validar se os dados essenciais estão presentes
        if (qrCode == null || qrCode.trim().isEmpty()) {
            log.error("QR Code PIX não foi gerado pelo Mercado Pago");
            throw new RuntimeException("QR Code PIX não foi gerado pelo Mercado Pago");
        }

        log.debug("PIX gerado com sucesso - QR Code: {}...", qrCode.substring(0, Math.min(50, qrCode.length())));

        return new PixPaymentLinks(
                qrCode,
                qrCodeBase64,
                ticketUrl
        );
    }

}