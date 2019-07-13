package com.datahack.eventdeliveroo.order.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.domain.model.OrderState;

@Component
class OrderBuilder implements IOrderBuilder {

  @Override
  public Order createOrder() {

    return Order.builder()
        .orderId(UUID.randomUUID().toString())
        .orderState(OrderState.ORDERED)
        .build();
  }
}
