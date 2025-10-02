package br.com.sunnypay.order.controller;

import br.com.sunnypay.order.dto.OrderResponse;
import br.com.sunnypay.order.dto.OrderRequest;
import br.com.sunnypay.order.service.OrderServiceResolver;
import br.com.sunnypay.payment.domain.CustomerPaymentCredentials;
import br.com.sunnypay.payment.repository.ClientPaymentConfigRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final ClientPaymentConfigRepository clientPaymentConfigRepository;
    private final OrderServiceResolver orderServiceResolver;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest, @RequestHeader(name = "X-API-Key") String apiKey) {
        log.info(apiKey);
        final var clientPaymentConfig = clientPaymentConfigRepository.findByApiKey(apiKey)
                .filter(CustomerPaymentCredentials::getActive)
                .orElseThrow(() -> new NoSuchElementException("No such element"));

        var processedOrderInfo = orderServiceResolver
                .resolve(orderRequest.paymentMethod())
                .process(orderRequest, clientPaymentConfig);

        return ResponseEntity.created(URI.create("xpto")).body(processedOrderInfo);
    }
}
