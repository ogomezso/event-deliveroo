package com.datahack.eventdeliveroo.order.infrastructure.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.domain.service.IOrderBuilder;
import com.datahack.eventdeliveroo.order.infrastructure.mongo.OrderDas;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class OrderService implements IOrder {

  private final IOrderBuilder orderBuilder;
  private final OrderDas orderDas;

  @Autowired
  public OrderService(IOrderBuilder orderBuilder,
      OrderDas orderDas) {
    this.orderBuilder = orderBuilder;
    this.orderDas = orderDas;
  }

  @Override
  public Mono<Order> placeOrder() throws Exception {

    log.debug("place holder");
    Order order = Optional.ofNullable(orderBuilder.createOrder())
        .orElseThrow(()-> new Exception("order must not be null"));

    return orderDas.saveOrder(order);
  }

  @Override
  public Order retrieveOrder(String order) {
    return null;
  }
}
