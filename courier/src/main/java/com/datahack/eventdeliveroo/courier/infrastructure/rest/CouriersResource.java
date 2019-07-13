package com.datahack.eventdeliveroo.courier.infrastructure.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.datahack.eventdeliveroo.courier.infrastructure.service.ICourier;

@Controller
public class CouriersResource {

  private final ICourier courierManager;

  public CouriersResource(
      ICourier courierManager) {
    this.courierManager = courierManager;
  }

  @PostMapping(value = "/couriers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<String> setNewAvailableCourier() {

    return ResponseEntity.ok().body(courierManager.setNewAvailableCourier());
  }

}
