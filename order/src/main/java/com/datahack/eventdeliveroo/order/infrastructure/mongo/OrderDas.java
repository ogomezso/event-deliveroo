package com.datahack.eventdeliveroo.order.infrastructure.mongo;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.infrastructure.mongo.model.OrderDocument;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class OrderDas {

  private final OrderMongoAdapter orderMongoAdapter;
  private final OrderRepository orderRepository;

  public OrderDas(
      OrderMongoAdapter orderMongoAdapter,
      OrderRepository orderRepository) {
    this.orderMongoAdapter = orderMongoAdapter;
    this.orderRepository = orderRepository;
  }

  public Mono<Order> saveOrder(Order order) {

    OrderDocument document2Persist = orderMongoAdapter.domain2Document(order);

    Mono<OrderDocument> documentPersisted = orderRepository.save(document2Persist);

    return documentPersisted
        .onErrorResume(t -> Mono.error(new Exception()))
        .map(orderMongoAdapter::document2Domain);

  }

  public Order retrieveOrder(String order) {
    return null;
  }
}
