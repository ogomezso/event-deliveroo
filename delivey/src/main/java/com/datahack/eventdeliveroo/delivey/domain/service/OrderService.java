package com.datahack.eventdeliveroo.delivey.domain.service;

import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.delivey.domain.model.Order;
import com.datahack.eventdeliveroo.delivey.domain.model.OrderState;


@Component
class OrderService implements IOrder {


  @Override
  public Order updateOrderStatus(Order order) {
    return Order.builder()
        .orderId(order.getOrderId())
        .orderState(OrderState.DELIVERED)
        .build();
  }
}
