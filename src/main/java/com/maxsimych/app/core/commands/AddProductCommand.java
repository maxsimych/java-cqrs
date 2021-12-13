package com.maxsimych.app.core.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class AddProductCommand {
  @TargetAggregateIdentifier private final String orderId;
  private final String productId;
}
