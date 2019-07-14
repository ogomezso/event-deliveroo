package com.datahack.eventdeliveroo.delivey.domain.service;


import com.datahack.eventdeliveroo.delivey.domain.model.Order;

public interface IOrder {

  Order updateOrderStatus(Order order);
}
