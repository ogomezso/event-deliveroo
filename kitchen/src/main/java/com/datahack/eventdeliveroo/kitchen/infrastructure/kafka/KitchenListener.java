package com.datahack.eventdeliveroo.kitchen.infrastructure.kafka;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.kitchen.domain.model.Order;
import com.datahack.eventdeliveroo.kitchen.infrastructure.kafka.adapter.KafkaAdapter;
import com.datahack.eventdeliveroo.kitchen.infrastructure.service.IEventManager;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class KitchenListener {

  private final IEventManager eventManager;
  private final KafkaAdapter kafkaAdapter;

  public KitchenListener(
      IEventManager eventManager,
      KafkaAdapter kafkaAdapter) {
    this.eventManager = eventManager;
    this.kafkaAdapter = kafkaAdapter;
  }

  @KafkaListener(topics = "${kafka.listenerTopic}")
  public void consume(String message) throws InterruptedException, IOException {

    Order order = kafkaAdapter.createDomainOrderFromOrderEvent(message);

    log.info("Order Received ID: {}, Status_{}", order.getOrderId(), order.getOrderState());

    eventManager.manageEvent(order);

  }

}
