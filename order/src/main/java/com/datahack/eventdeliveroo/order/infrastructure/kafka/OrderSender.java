package com.datahack.eventdeliveroo.order.infrastructure.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.infrastructure.kafka.config.KafkaConfigProp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
class OrderSender implements IOrderSender {

  private final KafkaConfigProp kafkaConfigProp;
  private final KafkaTemplate<String, Order> kafkaTemplate;

  public OrderSender(
      KafkaConfigProp kafkaConfigProp,
      KafkaTemplate<String, Order> kafkaTemplate) {
    this.kafkaConfigProp = kafkaConfigProp;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(Order order) {

    log.info("OrderSender.send {}", order);

    ListenableFuture<SendResult<String, Order>> resultFuture = kafkaTemplate
        .send(kafkaConfigProp.getSenderTopic(), order.getOrderId(), order);

    resultFuture.addCallback(new ListenableFutureCallback<SendResult<String, Order>>() {
      @Override
      public void onFailure(Throwable throwable) {
        log.error("Unable to Send Order with Id: {}", order.getOrderId());
      }

      @Override
      public void onSuccess(SendResult<String, Order> result) {
        log.info("Order with orderId:{} send to kitchen. Event Offset: {}", order.getOrderId(),
            result.getRecordMetadata().offset());
      }
    });

  }
}
