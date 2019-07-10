package com.datahack.eventdeliveroo.order.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.domain.model.OrderState;

@Component
class OrderBuilder implements IOrderBuilder {

  @Override
  public Order createOrder() {

    return Order.builder()
        .id(UUID.randomUUID().toString())
        .date(LocalDateTime.now())
        .orderState(OrderState.ORDERED)
        .build();
  }
}
