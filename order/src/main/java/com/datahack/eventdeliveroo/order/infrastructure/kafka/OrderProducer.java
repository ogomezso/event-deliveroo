package com.datahack.eventdeliveroo.order.infrastructure.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.infrastructure.kafka.adapter.KafkaAdapter;
import com.datahack.eventdeliveroo.order.infrastructure.kafka.config.KafkaConfigProp;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
class OrderProducer implements IOrderProducer {

  private final KafkaConfigProp kafkaConfigProp;
  private final KafkaTemplate<String, String > kafkaTemplate;
  private final KafkaAdapter kafkaAdapter;

  public OrderProducer(
      KafkaConfigProp kafkaConfigProp,
      KafkaTemplate<String, String> kafkaTemplate,
      KafkaAdapter kafkaAdapter) {
    this.kafkaConfigProp = kafkaConfigProp;
    this.kafkaTemplate = kafkaTemplate;
    this.kafkaAdapter = kafkaAdapter;
  }

  public void send(Order order) throws JsonProcessingException {

    log.info("OrderSender.send {}", order);

    String orderEvent = kafkaAdapter.createOrderEventFromDomainOrder(order);

    ListenableFuture<SendResult<String, String>> resultFuture = kafkaTemplate
        .send(kafkaConfigProp.getSenderTopic(), order.getOrderId(), orderEvent);

    resultFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
      @Override
      public void onFailure(Throwable throwable) {
        log.error("Unable to Send Order with Id: {}", order.getOrderId());
      }

      @Override
      public void onSuccess(SendResult<String, String> result) {
        log.info("Order with orderId:{} send to kitchen. Event Offset: {}", order.getOrderId(),
            result.getRecordMetadata().offset());
      }
    });

  }
}
