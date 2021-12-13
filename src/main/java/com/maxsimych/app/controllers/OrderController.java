package com.maxsimych.app.controllers;

import com.maxsimych.app.core.commands.AddOrderCommand;
import com.maxsimych.app.core.commands.ConfirmOrderCommand;
import com.maxsimych.app.core.commands.ShipOrderCommand;
import com.maxsimych.app.core.queries.FindAllOrderProductsQuery;
import com.maxsimych.app.core.queries.Order;
import lombok.NonNull;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("orders")
public class OrderController {
  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  public OrderController(CommandGateway commandGateway, QueryGateway queryGateway) {
    this.commandGateway = commandGateway;
    this.queryGateway = queryGateway;
  }

  @GetMapping("/")
  public CompletableFuture<List<Order>> findAllOrders() {
    return queryGateway.query(
        new FindAllOrderProductsQuery(), ResponseTypes.multipleInstancesOf(Order.class));
  }

  @PostMapping("add")
  public CompletableFuture<Void> add(@NonNull @RequestBody OrderBody body) {
    return commandGateway.send(new AddOrderCommand(body.orderId()));
  }

  @PostMapping("confirm")
  public CompletableFuture<Void> confirm(@NonNull @RequestBody OrderBody body) {
    return commandGateway.send(new ConfirmOrderCommand(body.orderId()));
  }

  @PostMapping("ship")
  public CompletableFuture<Void> ship(@NonNull @RequestBody OrderBody body) {
    return commandGateway.send(new ShipOrderCommand(body.orderId()));
  }
}
