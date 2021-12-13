package com.maxsimych.app.core.exceptions;

public class DuplicateOrderLineException extends IllegalStateException {
  public DuplicateOrderLineException(String productId) {
    super("Cannot duplicate order line for product id " + productId);
  }
}
