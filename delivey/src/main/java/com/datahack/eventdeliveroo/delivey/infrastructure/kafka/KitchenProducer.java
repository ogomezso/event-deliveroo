package com.datahack.eventdeliveroo.delivey.infrastructure.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.datahack.eventdeliveroo.delivey.domain.model.Order;
import com.datahack.eventdeliveroo.delivey.infrastructure.kafka.adapter.KafkaAdapter;
import com.datahack.eventdeliveroo.delivey.infrastructure.kafka.config.KafkaConfigProp;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KitchenProducer implements IKitchenProducer{

  private final KafkaAdapter kafkaAdapter;
  private final KafkaConfigProp kafkaConfigProp;
  private final KafkaTemplate<String, String> kafkaTemplate;

  public KitchenProducer(
      KafkaAdapter kafkaAdapter,
      KafkaConfigProp kafkaConfigProp,
      KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaAdapter = kafkaAdapter;
    this.kafkaConfigProp = kafkaConfigProp;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void send(Order order) throws JsonProcessingException {

    log.info("OrderSender.send {}", order);

    String orderEvent = kafkaAdapter.createOrderEventFromDomainOrder(order);

    ListenableFuture<SendResult<String, String>> resultFuture = kafkaTemplate
        .send(kafkaConfigProp.getTopic(), order.getOrderId(), orderEvent);

    resultFuture.addCallback(new ListenableFutureCallback<>() {
      @Override
      public void onFailure(Throwable throwable) {
        log.error("Unable to Send Order with Id: {}", order.getOrderId());
      }

      @Override
      public void onSuccess(SendResult<String, String> result) {
        log.info("Order with orderId:{} is ready to deliver. Event Offset: {}", order.getOrderId(),
            result.getRecordMetadata().offset());
      }
    });
  }

}
