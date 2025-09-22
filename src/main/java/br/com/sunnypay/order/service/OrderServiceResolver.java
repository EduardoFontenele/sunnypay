package br.com.sunnypay.order.service;

import br.com.sunnypay.order.domain.PaymentMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
@Slf4j
public class OrderServiceResolver {
    private final Map<PaymentMethod, OrderService> services;

    public OrderServiceResolver(ApplicationContext applicationContext) {
        this.services = new EnumMap<>(PaymentMethod.class);

        for (var method : PaymentMethod.values()) {
            try {
                var strategy = applicationContext.getBean(method.name(), OrderService.class);
                this.services.put(method, strategy);
            } catch (NoSuchBeanDefinitionException ex) {
                log.warn("No strategy found for payment method: {}", method);
            }
        }
    }

    public OrderService resolve(PaymentMethod paymentMethod) {
        return services.get(paymentMethod);
    }
}
