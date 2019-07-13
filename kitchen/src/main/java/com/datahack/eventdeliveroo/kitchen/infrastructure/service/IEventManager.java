package com.datahack.eventdeliveroo.kitchen.infrastructure.service;

import com.datahack.eventdeliveroo.kitchen.domain.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IEventManager {

  void manageEvent(Order incomingEvent) throws InterruptedException, JsonProcessingException;

}
