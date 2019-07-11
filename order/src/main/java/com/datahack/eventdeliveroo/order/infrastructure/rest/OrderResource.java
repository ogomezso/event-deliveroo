package com.datahack.eventdeliveroo.order.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.infrastructure.service.IOrder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Controller
@Slf4j
public class OrderResource {

  private final IOrder orderService;
  private final EmitterProcessor<Order> emitterProcessor;

  @Autowired
  public OrderResource(IOrder orderService,
      EmitterProcessor<Order> emitterProcessor) {
    this.orderService = orderService;
    this.emitterProcessor = emitterProcessor;
  }

  @PostMapping("/orders")
  public ResponseEntity<Mono<String>> placeOrder() throws Exception {

    Mono<Order> order = orderService.placeOrder();

    return ResponseEntity.ok()
        .body(
            order.map(Order::getId).doOnNext(m -> log.info("ok!"))
        );
  }

  @GetMapping("/orders")
  public ResponseEntity<Flux<Order>> getOrderState() {

    log.info("get order state");

    return ResponseEntity.ok().header("X-Accel-Buffering", "no")
        .body(emitterProcessor.subscribeOn(Schedulers.single())
            .doOnNext(o -> log.info("Order with Id {} change Status to {}",o.getId(), o.getOrderState().name())));

  }
}
