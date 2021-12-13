package com.maxsimych.app.core.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class AddOrderCommand {
  @TargetAggregateIdentifier private final String orderId;
}
