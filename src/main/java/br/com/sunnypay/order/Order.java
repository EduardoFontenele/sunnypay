package br.com.sunnypay.order;

public interface Order {
    String getOrderId();
    String getReferenceId();
    OrderStatus getOrderStatus();
}
