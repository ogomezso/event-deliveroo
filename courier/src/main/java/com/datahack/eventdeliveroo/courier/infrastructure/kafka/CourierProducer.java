package com.datahack.eventdeliveroo.courier.infrastructure.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.datahack.eventdeliveroo.courier.infrastructure.kafka.config.KafkaConfigProp;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class CourierProducer implements ICourierProducer {

  private final KafkaConfigProp kafkaConfigProp;
  private final KafkaTemplate<String, String> kafkaTemplate;

  CourierProducer(
      KafkaConfigProp kafkaConfigProp,
      KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaConfigProp = kafkaConfigProp;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(String courier) {

    log.info("courier.send {}", courier);

    ListenableFuture<SendResult<String, String>> resultFuture = kafkaTemplate
        .send(kafkaConfigProp.getSenderTopic(), null, courier);

    resultFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
      @Override
      public void onFailure(Throwable throwable) {
        log.error("Unable to Send new Courier with Id: {}", courier);
      }

      @Override
      public void onSuccess(SendResult<String, String> result) {
        log.info("New Courier Aviable with id:{}. Event Offset: {}", courier,
            result.getRecordMetadata().offset());
      }
    });
  }
}
