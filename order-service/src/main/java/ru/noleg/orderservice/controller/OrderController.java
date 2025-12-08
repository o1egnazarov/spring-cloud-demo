package ru.noleg.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.noleg.orderservice.dto.OrderCreatedRequest;
import ru.noleg.orderservice.entity.Order;
import ru.noleg.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order create(@RequestBody OrderCreatedRequest orderCreatedRequest) {
        return orderService.createOrder(orderCreatedRequest);
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }
}