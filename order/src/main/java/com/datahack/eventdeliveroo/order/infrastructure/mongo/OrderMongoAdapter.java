package com.datahack.eventdeliveroo.order.infrastructure.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.infrastructure.mongo.model.OrderDocument;

@Component
class OrderMongoAdapter {

  private final OrderMongoMapper orderMongoMapper;

  @Autowired
  OrderMongoAdapter(
      OrderMongoMapper orderMongoMapper) {
    this.orderMongoMapper = orderMongoMapper;
  }

  OrderDocument domain2Document(Order order) {
    return orderMongoMapper.domain2Document(order);
  }

  Order document2Domain(OrderDocument documentPersisted) {

    return orderMongoMapper.document2Domain(documentPersisted);
  }
}
