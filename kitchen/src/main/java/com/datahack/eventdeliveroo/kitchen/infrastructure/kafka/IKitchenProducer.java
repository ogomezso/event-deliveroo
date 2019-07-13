package com.datahack.eventdeliveroo.kitchen.infrastructure.kafka;

import com.datahack.eventdeliveroo.kitchen.domain.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IKitchenProducer {

  void send(Order order) throws JsonProcessingException;

}
