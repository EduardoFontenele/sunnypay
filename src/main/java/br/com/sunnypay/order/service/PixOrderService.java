package br.com.sunnypay.order.service;

import br.com.sunnypay.order.domain.Order;
import br.com.sunnypay.order.dto.OrderResponse;
import br.com.sunnypay.order.dto.OrderRequest;
import br.com.sunnypay.order.dto.PixOrderResponse;
import br.com.sunnypay.order.repository.OrderRepository;
import br.com.sunnypay.payment.domain.ClientPaymentConfig;
import br.com.sunnypay.payment.service.PixPaymentService;
import br.com.sunnypay.shared.config.BusinessLogicErrorMessages;
import br.com.sunnypay.shared.domain.Item;
import br.com.sunnypay.shared.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service("PIX")
public class PixOrderService implements OrderService {
    private final PixPaymentService pixPaymentService;
    private final BusinessLogicErrorMessages businessLogicErrorMessages;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse process(OrderRequest orderRequest, ClientPaymentConfig paymentConfig) {
        log.info("Enviando items de pedido para processamento de pagamento...");

        validateRequestAmount(orderRequest.amountInCents(), orderRequest.items());

        var savedOrder = orderRepository.save(new Order(
                orderRequest.amountInCents(),
                orderRequest.customer().name(),
                orderRequest.customer().email()
                )
        );
        log.info(savedOrder.getReferenceId().toString());

        var paymentLinks = pixPaymentService.process(orderRequest.amountInCents());

        return new PixOrderResponse(
                savedOrder.getReferenceId().toString(),
                savedOrder.getStatus(),
                paymentLinks
        );
    }

    private void validateRequestAmount(Long totalAmount, List<Item> items) {
        var calculatedTotal = items.stream()
                .mapToLong(item -> item.quantity() * item.unitAmount())
                .sum();

        if(calculatedTotal != totalAmount) {
            throw new BusinessLogicException(businessLogicErrorMessages.getIncompatibleTotalAmounts(), HttpStatus.BAD_REQUEST);
        }
    }
}
