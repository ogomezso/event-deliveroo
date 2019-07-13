package com.datahack.eventdeliveroo.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Order {

  private String orderId;
  private String courierId;
  private OrderState orderState;
}
