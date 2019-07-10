package com.datahack.eventdeliveroo.order.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Order {

  private String id;
  private String courierId;
  private LocalDateTime date;
  private OrderState orderState;
}
