package com.datahack.eventdeliveroo.order.infrastructure.kafka;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IOrderProducer {

  void send(Order order) throws JsonProcessingException;

}
