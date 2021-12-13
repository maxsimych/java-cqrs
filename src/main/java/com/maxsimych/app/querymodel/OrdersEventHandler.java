package com.maxsimych.app.querymodel;

import com.maxsimych.app.core.events.*;
import com.maxsimych.app.core.queries.FindAllOrderProductsQuery;
import com.maxsimych.app.core.queries.Order;
import lombok.NonNull;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ProcessingGroup("orders")
public class OrdersEventHandler {
  private final Map<String, Order> orders = new HashMap<>();

  @QueryHandler
  public List<Order> handle(FindAllOrderProductsQuery query) {
    return new ArrayList<>(orders.values());
  }

  @EventHandler
  public void on(@NonNull OrderCreatedEvent event) {
    String orderId = event.getOrderId();
    orders.put(orderId, new Order(orderId));
  }

  @EventHandler
  public void on(@NonNull ProductAddedEvent event) {
    orders.computeIfPresent(
        event.getOrderId(),
        (orderId, order) -> {
          order.addProduct(event.getProductId());
          return order;
        });
  }

  @EventHandler
  public void on(@NonNull OrderConfirmedEvent event) {
    orders.computeIfPresent(
        event.getOrderId(),
        (orderId, order) -> {
          order.setOrderConfirmed();
          return order;
        });
  }

  @EventHandler
  public void on(@NonNull OrderShippedEvent event) {
    orders.computeIfPresent(
        event.getOrderId(),
        (orderId, order) -> {
          order.setOrderShipped();
          return order;
        });
  }

  @EventHandler
  public void on(@NonNull ProductIncrementedEvent event) {
    orders.computeIfPresent(
        event.getOrderId(),
        (orderId, order) -> {
          order.incrementProductInstance(event.getProductId());
          return order;
        });
  }

  @EventHandler
  public void on(@NonNull ProductDecrementedEvent event) {
    orders.computeIfPresent(
        event.getOrderId(),
        (orderId, order) -> {
          order.decrementProductInstance(event.getProductId());
          return order;
        });
  }
}
