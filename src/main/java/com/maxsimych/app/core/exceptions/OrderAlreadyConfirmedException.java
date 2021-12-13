package com.maxsimych.app.core.exceptions;

public class OrderAlreadyConfirmedException extends IllegalStateException {
  public OrderAlreadyConfirmedException(String orderId) {
    super("Order " + orderId + " is already confirmed.");
  }
}
