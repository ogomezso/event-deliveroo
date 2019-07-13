package com.datahack.eventdeliveroo.order.infrastructure.service;

import com.datahack.eventdeliveroo.order.domain.model.Order;

import reactor.core.publisher.Mono;

public interface IOrder {

  Mono<Order> placeOrder() throws Exception;

  void updateOrder(Order order) throws Exception;

}
