package com.datahack.eventdeliveroo.delivey.domain.model;

import java.io.Serializable;

public enum OrderState implements Serializable {

  DELIVERED("delivered"),
  ERROR("error"),
  ORDERED("ordered"),
  READY("ready");


  public final String label;

  OrderState(String label) {
    this.label = label;
  }
}
