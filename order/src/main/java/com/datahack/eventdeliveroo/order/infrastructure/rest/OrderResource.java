package com.datahack.eventdeliveroo.order.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.datahack.eventdeliveroo.order.domain.model.Order;
import com.datahack.eventdeliveroo.order.infrastructure.mongo.OrderDas;
import com.datahack.eventdeliveroo.order.infrastructure.service.IOrder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class OrderResource {

  private final IOrder orderService;
  private final OrderDas orderDas;

  @Autowired
  public OrderResource(IOrder orderService,
      OrderDas orderDas) {
    this.orderService = orderService;
    this.orderDas = orderDas;
  }

  @CrossOrigin
  @PostMapping("/orders")
  public ResponseEntity<Mono<String>> placeOrder() throws Exception {

    Mono<Order> order = orderService.placeOrder();

    return ResponseEntity.ok()
        .body(
            order.map(Order::getOrderId).doOnNext(m -> log.info("ok!"))
        );
  }

//  @PutMapping(value = "/orders", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
//  public ResponseEntity<Mono<Order>> updateOrderStatus(@RequestBody Order order) throws Exception {
//    log.info("update Order reource");
//    return ResponseEntity.ok()
//        .body(orderService.updateOrder(order)
//            .doOnNext(r -> log.info("ok")));
//  }


  @CrossOrigin
  @GetMapping(value = "/orders", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<Flux<Order>> orderContiniousQuery() {

    log.info("get order state stream");

    return ResponseEntity.ok().body(orderDas.getOrderStream());

  }
}
