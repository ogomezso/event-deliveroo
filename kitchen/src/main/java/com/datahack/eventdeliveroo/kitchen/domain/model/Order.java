package com.datahack.eventdeliveroo.kitchen.domain.model;

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
  private String courierId;
  private OrderState orderState;
}
