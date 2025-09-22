package br.com.sunnypay.payment.service;

import br.com.sunnypay.shared.domain.PixPaymentLinks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PixPaymentService {
    public PixPaymentLinks process(Long totalAmount) {
        log.info("Processando items para pagamentos...");
        return null;
    }
}
