package com.maxsimych.app.core.queries;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
@ToString
public class Order {
  private final String orderId;
  private final Map<String, Integer> products;
  private OrderStatus orderStatus;

  public Order(String orderId) {
    this.orderId = orderId;
    this.products = new HashMap<>();
    orderStatus = OrderStatus.CREATED;
  }

  public String orderId() {
    return orderId;
  }

  public void setOrderConfirmed() {
    this.orderStatus = OrderStatus.CONFIRMED;
  }

  public void setOrderShipped() {
    this.orderStatus = OrderStatus.SHIPPED;
  }

  public void addProduct(String productId) {
    products.putIfAbsent(productId, 1);
  }

  public void incrementProductInstance(String productId) {
    products.computeIfPresent(productId, (id, count) -> ++count);
  }

  public void decrementProductInstance(String productId) {
    products.computeIfPresent(productId, (id, count) -> --count);
  }
}
