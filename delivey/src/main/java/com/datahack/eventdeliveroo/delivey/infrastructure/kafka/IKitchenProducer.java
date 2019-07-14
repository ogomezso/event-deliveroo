package com.datahack.eventdeliveroo.delivey.infrastructure.kafka;

import com.datahack.eventdeliveroo.delivey.domain.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IKitchenProducer {

  void send(Order order) throws JsonProcessingException;

}
