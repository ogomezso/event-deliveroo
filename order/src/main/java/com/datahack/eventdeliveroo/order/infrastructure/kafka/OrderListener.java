package com.datahack.eventdeliveroo.order.infrastructure.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.domain.model.OrderState;
import com.datahack.eventdeliveroo.order.infrastructure.service.IOrder;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class OrderListener {

  private final IOrder orderService;

  OrderListener(IOrder orderService) {
    this.orderService = orderService;
  }


  @KafkaListener(topics = "${kafka.listenerTopic}")
  public void consume(Order order) throws Exception {
    log.info("Order Received ID: {}, Status_{}", order.getOrderId(), order.getOrderState());

    order.setOrderState(OrderState.READY);
    orderService.updateOrder(order);

  }
}
