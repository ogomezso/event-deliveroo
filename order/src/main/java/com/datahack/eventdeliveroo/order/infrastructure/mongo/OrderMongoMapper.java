package com.datahack.eventdeliveroo.order.infrastructure.mongo;

import org.mapstruct.Mapper;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.infrastructure.mongo.model.OrderDocument;

@Mapper(componentModel = "spring")
interface OrderMongoMapper {

  OrderDocument domain2Document(Order order);

  Order document2Domain(OrderDocument document);

}
