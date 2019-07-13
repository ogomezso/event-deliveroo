package com.datahack.eventdeliveroo.order.infrastructure.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.datahack.eventdeliveroo.order.domain.model.OrderState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@Document(collection = "orders")
@NoArgsConstructor
public class OrderDocument {

  @Id
  private String documentId;
  private String orderId;
  private String courierId;
  private OrderState orderState;

}
