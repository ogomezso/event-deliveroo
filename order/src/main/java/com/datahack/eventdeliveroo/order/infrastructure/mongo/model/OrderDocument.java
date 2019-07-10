package com.datahack.eventdeliveroo.order.infrastructure.mongo.model;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
  @NotEmpty
  private String id;
  private String courierId;
  private LocalDateTime date;
  private OrderState orderState;

}
