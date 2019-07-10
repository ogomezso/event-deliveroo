package com.datahack.eventdeliveroo.order.infrastructure.kafka;

import com.datahack.eventdeliveroo.order.domain.model.Order;

public interface IOrderSender {

  void send(Order order);

}
