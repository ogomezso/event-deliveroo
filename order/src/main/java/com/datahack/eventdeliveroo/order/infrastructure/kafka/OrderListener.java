package com.datahack.eventdeliveroo.order.infrastructure.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.domain.model.OrderState;
import com.datahack.eventdeliveroo.order.infrastructure.kafka.adapter.KafkaAdapter;
import com.datahack.eventdeliveroo.order.infrastructure.service.IOrder;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class OrderListener {

  private final IOrder orderService;
  private final KafkaAdapter kafkaAdapter;

  OrderListener(IOrder orderService,
      KafkaAdapter kafkaAdapter) {
    this.orderService = orderService;
    this.kafkaAdapter = kafkaAdapter;
  }


  @KafkaListener(topics = "${kafka.listenerTopic}")
  public void consume(String message) throws Exception {

    Order order = kafkaAdapter.createDomainOrderFromOrderEvent(message);
    order.setOrderState(OrderState.READY);

    log.info("Order Received ID: {}, Status_{}", order.getOrderId(), order.getOrderState());

    orderService.updateOrder(order);

  }
}
