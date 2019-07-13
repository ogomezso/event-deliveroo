package com.datahack.eventdeliveroo.kitchen.domain.service;

import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.kitchen.domain.model.Order;
import com.datahack.eventdeliveroo.kitchen.domain.model.OrderState;

@Component
class OrderService implements IOrder {


  @Override
  public Order updateOrderStatus(Order order) {
    return Order.builder()
        .orderId(order.getOrderId())
        .courierId(order.getCourierId())
        .orderState(OrderState.READY)
        .build();
  }
}
