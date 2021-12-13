package com.maxsimych.app.core.events;

import lombok.Data;

@Data
public class OrderShippedEvent {
  private final String orderId;
}
