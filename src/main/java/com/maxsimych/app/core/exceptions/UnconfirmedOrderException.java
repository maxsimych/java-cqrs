package com.maxsimych.app.core.exceptions;

public class UnconfirmedOrderException extends IllegalStateException {
  public UnconfirmedOrderException() {
    super("Order has not been confirmed");
  }
}
