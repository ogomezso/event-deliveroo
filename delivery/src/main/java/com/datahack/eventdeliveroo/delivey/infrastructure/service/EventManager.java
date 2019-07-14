package com.datahack.eventdeliveroo.delivey.infrastructure.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.delivey.domain.model.Order;
import com.datahack.eventdeliveroo.delivey.domain.service.IOrder;
import com.datahack.eventdeliveroo.delivey.infrastructure.kafka.IKitchenProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EventManager implements IEventManager {


  private final IOrder orderService;
  private final IKitchenProducer kitchenProducer;

  public EventManager(IOrder orderService,
      IKitchenProducer kitchenProducer) {
    this.orderService = orderService;
    this.kitchenProducer = kitchenProducer;
  }

  @Override
  public void manageEvent(Order incomingEvent)
      throws InterruptedException, JsonProcessingException {

    Order orderUpdated = orderService.updateOrderStatus(incomingEvent);
    Random random = new Random();
    TimeUnit.SECONDS.sleep(random.nextInt(30));
    sendOrderStatusChangeEvent(orderUpdated);
  }

  private void sendOrderStatusChangeEvent(Order outgoingEvent) throws JsonProcessingException {

    kitchenProducer.send(outgoingEvent);
  }
}