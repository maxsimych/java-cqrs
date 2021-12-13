package com.maxsimych.app.commandmodel.order;

import com.maxsimych.app.core.commands.AddProductCommand;
import com.maxsimych.app.core.commands.ConfirmOrderCommand;
import com.maxsimych.app.core.commands.AddOrderCommand;
import com.maxsimych.app.core.commands.ShipOrderCommand;
import com.maxsimych.app.core.events.*;
import com.maxsimych.app.core.exceptions.DuplicateOrderLineException;
import com.maxsimych.app.core.exceptions.OrderAlreadyConfirmedException;
import com.maxsimych.app.core.exceptions.UnconfirmedOrderException;
import lombok.NonNull;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate(snapshotTriggerDefinition = "orderAggregateSnapshotTriggerDefinition")
public class OrderAggregate {

  @AggregateIdentifier private String orderId;
  private boolean orderConfirmed;

  @AggregateMember private Map<String, OrderLine> orderLines;

  @CommandHandler
  public OrderAggregate(@NonNull AddOrderCommand command) {
    apply(new OrderCreatedEvent(command.getOrderId()));
  }

  @CommandHandler
  public void handle(ConfirmOrderCommand command) {
    if (orderConfirmed) {
      return;
    }
    apply(new OrderConfirmedEvent(command.getOrderId()));
  }

  @CommandHandler
  public void handle(ShipOrderCommand command) {
    if (!orderConfirmed) {
      throw new UnconfirmedOrderException();
    }
    apply(new OrderShippedEvent(orderId));
  }

  @CommandHandler
  public void handle(AddProductCommand command) {
    if (orderConfirmed) {
      throw new OrderAlreadyConfirmedException(orderId);
    }

    String productId = command.getProductId();
    if (orderLines.containsKey(productId)) {
      throw new DuplicateOrderLineException(productId);
    }
    apply(new ProductAddedEvent(orderId, productId));
  }

  @EventSourcingHandler
  public void on(@NonNull ProductAddedEvent event) {
    var productId = event.getProductId();
    orderLines.put(productId, new OrderLine(productId));
  }

  @EventSourcingHandler
  public void on(@NonNull OrderCreatedEvent event) {
    orderId = event.getOrderId();
    orderLines = new HashMap<>();
    orderConfirmed = false;
  }

  @EventSourcingHandler
  public void on(OrderConfirmedEvent event) {
    this.orderConfirmed = true;
  }

  @EventSourcingHandler
  public void on(@NonNull ProductRemovedEvent event) {
    this.orderLines.remove(event.getProductId());
  }

  protected OrderAggregate() {
    // Required by Axon to build a default Aggregate prior to Event Sourcing
  }
}
