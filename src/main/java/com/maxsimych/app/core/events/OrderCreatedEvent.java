package com.maxsimych.app.core.events;

import lombok.Data;

@Data
public class OrderCreatedEvent {
  private final String orderId;
}
