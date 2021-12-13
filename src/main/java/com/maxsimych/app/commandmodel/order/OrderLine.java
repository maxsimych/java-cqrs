package com.maxsimych.app.commandmodel.order;

import com.maxsimych.app.core.commands.DecProductCommand;
import com.maxsimych.app.core.commands.IncProductCommand;
import com.maxsimych.app.core.events.OrderConfirmedEvent;
import com.maxsimych.app.core.events.ProductDecrementedEvent;
import com.maxsimych.app.core.events.ProductIncrementedEvent;
import com.maxsimych.app.core.events.ProductRemovedEvent;
import com.maxsimych.app.core.exceptions.OrderAlreadyConfirmedException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

public class OrderLine {

  @EntityId private final String productId;
  private Integer count;
  private boolean orderConfirmed;

  public OrderLine(String productId) {
    this.productId = productId;
    this.count = 1;
  }

  @CommandHandler
  public void handle(IncProductCommand command) {
    if (orderConfirmed) {
      throw new OrderAlreadyConfirmedException(command.getOrderId());
    }
    apply(new ProductIncrementedEvent(command.getOrderId(), productId));
  }

  @CommandHandler
  public void handle(DecProductCommand command) {
    if (orderConfirmed) {
      throw new OrderAlreadyConfirmedException(command.getOrderId());
    }
    if (this.count <= 1) {
      apply(new ProductRemovedEvent(command.getOrderId(), productId));
      return;
    }
    apply(new ProductDecrementedEvent(command.getOrderId(), productId));
  }

  @EventSourcingHandler
  public void on(OrderConfirmedEvent event) {
    this.orderConfirmed = true;
  }

  @EventSourcingHandler
  public void on(ProductIncrementedEvent event) {
    this.count++;
  }

  @EventSourcingHandler
  public void on(ProductDecrementedEvent event) {
    this.count--;
  }
}
