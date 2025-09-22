package br.com.sunnypay.order.repository;

import br.com.sunnypay.order.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
