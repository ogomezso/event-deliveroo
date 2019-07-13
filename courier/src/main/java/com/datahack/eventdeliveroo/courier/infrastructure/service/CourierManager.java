package com.datahack.eventdeliveroo.courier.infrastructure.service;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.courier.infrastructure.kafka.ICourierProducer;

@Component
public class CourierManager implements ICourier {

  private final ICourierProducer courierProducer;

  public CourierManager(
      ICourierProducer courierProducer) {
    this.courierProducer = courierProducer;
  }

  @Override
  public String setNewAvailableCourier() {

    String courierId = getCourierId();

    courierProducer.send(courierId);

    return courierId;
  }

  private String getCourierId() {
    return UUID.randomUUID().toString();
  }
}
