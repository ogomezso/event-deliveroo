package com.datahack.eventdeliveroo.order.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.infrastructure.service.IOrder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class OrderResource {

  private final IOrder orderService;

  public OrderResource(IOrder orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/orders")
  public ResponseEntity<Mono<String>> placeOrder() throws Exception {

    Mono<Order> order = orderService.placeOrder();

    return ResponseEntity.ok()
        .body(
            order.map(orderId -> orderId.getId()).doOnNext(m -> log.info("ok!"))
        );
  }

  @GetMapping("/orders/{orderId}")
  public ResponseEntity getOrderState(@RequestParam String orderId) {

    return ResponseEntity.ok().build();

  }
}
