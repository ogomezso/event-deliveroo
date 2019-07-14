package com.datahack.eventdeliveroo.order.domain.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Order implements Serializable {

  private String orderId;
  private OrderState orderState;
}
