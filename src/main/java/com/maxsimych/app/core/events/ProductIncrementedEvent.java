package com.maxsimych.app.core.events;

import lombok.Data;

@Data
public class ProductIncrementedEvent {
  private final String orderId;
  private final String productId;
}
