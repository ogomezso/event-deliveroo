package com.datahack.eventdeliveroo.delivey.infrastructure.service;

import com.datahack.eventdeliveroo.delivey.domain.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IEventManager {

  void manageEvent(Order orderEvent)  throws InterruptedException, JsonProcessingException;

}
