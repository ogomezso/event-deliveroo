package com.datahack.eventdeliveroo.order.infrastructure.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.order.domain.model.Order;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.FluxSink;

@Component
@Slf4j
class OrderListener {

  private EmitterProcessor<Order> emitterProcessor;

  @Autowired
  OrderListener(
      EmitterProcessor<Order> emitterProcessor) {
    this.emitterProcessor = emitterProcessor;
  }

  @KafkaListener(topics = "${kafka.topic}")
  public void consume(Order order) {
    log.info("Order Received ID: {}, Status_{}", order.getId(), order.getOrderState());

    FluxSink<Order> incoming = this.emitterProcessor.sink();

    incoming.next(order);

  }
}
