package com.maxsimych.app.core.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class ConfirmOrderCommand {
  @TargetAggregateIdentifier private final String orderId;
}
