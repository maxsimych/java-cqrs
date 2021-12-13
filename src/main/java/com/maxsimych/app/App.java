package com.maxsimych.app;

import com.maxsimych.app.querymodel.OrdersEventHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
  @Bean
  public OrdersEventHandler getOrdersEventHandler() {
    return new OrdersEventHandler();
  }

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
