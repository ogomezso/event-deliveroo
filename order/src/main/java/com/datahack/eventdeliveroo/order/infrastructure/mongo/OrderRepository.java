package com.datahack.eventdeliveroo.order.infrastructure.mongo;

import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.datahack.eventdeliveroo.order.infrastructure.mongo.model.OrderDocument;

import reactor.core.publisher.Flux;

@Repository
interface OrderRepository extends ReactiveCrudRepository<OrderDocument, String> {

  @Tailable
  Flux<OrderDocument> findOrderDocumentBy();

  @Override
  default Flux<OrderDocument> findAll() {
    return findOrderDocumentBy();
  }
}
