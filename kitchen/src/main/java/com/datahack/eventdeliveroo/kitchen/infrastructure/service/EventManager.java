package com.datahack.eventdeliveroo.kitchen.infrastructure.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.kitchen.domain.model.Order;
import com.datahack.eventdeliveroo.kitchen.domain.service.IOrder;
import com.datahack.eventdeliveroo.kitchen.infrastructure.kafka.IKitchenProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class EventManager implements IEventManager {

  private final IOrder order;
  private final IKitchenProducer kitchenProducer;

  public EventManager(IOrder order,
      IKitchenProducer kitchenProducer) {
    this.order = order;
    this.kitchenProducer = kitchenProducer;
  }

  @Override
  public void manageEvent(Order incomingEvent) throws InterruptedException, JsonProcessingException {


    Order orderUpdated = order.updateOrderStatus(incomingEvent);
    Random random = new Random();
    TimeUnit.SECONDS.sleep(random.nextInt(10));
    sendOrderStatusChangeEvent(orderUpdated);
  }

  private void sendOrderStatusChangeEvent(Order outgoingEvent) throws JsonProcessingException {

    kitchenProducer.send(outgoingEvent);
  }
}
