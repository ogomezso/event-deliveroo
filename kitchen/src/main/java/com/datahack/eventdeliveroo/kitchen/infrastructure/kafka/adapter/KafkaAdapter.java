package com.datahack.eventdeliveroo.kitchen.infrastructure.kafka.adapter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.kitchen.domain.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaAdapter {

  private final ObjectMapper objectMapper;

  @Autowired
  public KafkaAdapter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String createOrderEventFromDomainOrder(Order order) throws JsonProcessingException {

    return objectMapper.writeValueAsString(order);
  }

  public Order createDomainOrderFromOrderEvent(String order) throws IOException {
    return objectMapper.readValue(order,Order.class);
  }

}
