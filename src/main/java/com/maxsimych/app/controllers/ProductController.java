package com.maxsimych.app.controllers;

import com.maxsimych.app.core.commands.AddProductCommand;
import com.maxsimych.app.core.commands.DecProductCommand;
import com.maxsimych.app.core.commands.IncProductCommand;
import lombok.NonNull;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("products")
public class ProductController {
  private final CommandGateway commandGateway;

  public ProductController(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @PostMapping("add")
  public CompletableFuture<Void> add(@NonNull @RequestBody ProductBody body) {
    return commandGateway.send(new AddProductCommand(body.orderId(), body.productId()));
  }

  @PostMapping("inc")
  public CompletableFuture<Void> inc(@NonNull @RequestBody ProductBody body) {
    return commandGateway.send(new IncProductCommand(body.orderId(), body.productId()));
  }

  @PostMapping("dec")
  public CompletableFuture<Void> dec(@NonNull @RequestBody ProductBody body) {
    return commandGateway.send(new DecProductCommand(body.orderId(), body.productId()));
  }
}
