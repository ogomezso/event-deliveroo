package com.datahack.eventdeliveroo.order.domain.model;

public enum OrderState {

  DELIVERED("delivered"),
  ERROR("error"),
  ORDERED("ordered"),
  READY("ready");


  public final String label;

  OrderState(String label) {
    this.label = label;
  }
}
