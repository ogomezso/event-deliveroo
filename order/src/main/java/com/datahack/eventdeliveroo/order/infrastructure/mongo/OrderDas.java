package com.datahack.eventdeliveroo.order.infrastructure.mongo;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.infrastructure.mongo.model.OrderDocument;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
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
    String documentId = generateDocumentId();
    document2Persist.setDocumentId(documentId);
    Mono<OrderDocument> documentPersisted = orderRepository.save(document2Persist);


    return documentPersisted
        .onErrorResume(t -> Mono.error(new Exception("Error on persist of Order Document")))
        .map(orderMongoAdapter::document2Domain);

  }

  public Flux<Order> getOrderStream() {
    return orderRepository.findOrderDocumentBy()
        .map(orderMongoAdapter::document2Domain);
  }

  private String generateDocumentId(){

    return UUID.randomUUID().toString();
  }
}
