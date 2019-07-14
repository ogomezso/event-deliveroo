package com.datahack.eventdeliveroo.delivey.infrastructure.kafka;

import static org.apache.kafka.common.requests.FetchMetadata.log;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.delivey.domain.model.Order;
import com.datahack.eventdeliveroo.delivey.domain.model.OrderState;
import com.datahack.eventdeliveroo.delivey.infrastructure.kafka.adapter.KafkaAdapter;
import com.datahack.eventdeliveroo.delivey.infrastructure.service.IEventManager;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KitchenListener {

  private final KafkaAdapter kafkaAdapter;
  private final IEventManager eventManager;

  public KitchenListener(
      KafkaAdapter kafkaAdapter,
      IEventManager eventManager) {
    this.kafkaAdapter = kafkaAdapter;
    this.eventManager = eventManager;
  }

  @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.groupId}")
  public void consume(String message) throws InterruptedException, IOException {

    Order order = kafkaAdapter.createDomainOrderFromOrderEvent(message);

    log.info("Order Received ID: {}, Status_{}", order.getOrderId(), order.getOrderState());

    if (order.getOrderState().equals(OrderState.READY)) {
      eventManager.manageEvent(order);
    }

  }

}
