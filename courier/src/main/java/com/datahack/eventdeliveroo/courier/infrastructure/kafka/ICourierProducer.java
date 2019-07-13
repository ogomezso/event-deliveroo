package com.datahack.eventdeliveroo.courier.infrastructure.kafka;

public interface ICourierProducer {

  void send(String courier);
}
