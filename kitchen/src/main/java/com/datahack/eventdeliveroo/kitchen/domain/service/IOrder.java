package com.datahack.eventdeliveroo.kitchen.domain.service;

import com.datahack.eventdeliveroo.kitchen.domain.model.Order;

public interface IOrder {

  public Order updateOrderStatus(Order order);
}
