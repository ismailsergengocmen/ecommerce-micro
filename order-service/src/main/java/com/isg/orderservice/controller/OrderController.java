package com.isg.orderservice.controller;

import com.isg.orderservice.dto.OrderRequest;
import com.isg.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //? Every call under this method will be in circuit breaker's scope
    //? fallbackMethod takes a function to be used as callback function
    @CircuitBreaker(name= "inventory", fallbackMethod = "fallbackMethod")
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order Placed Sucessfully";
    }

    //? The fallback method of circuit breaker should have same return type
    //? with original method covered by circuit breaker
    public String fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return "Oops! Something went wrong, please order after some time!";
    }
}
