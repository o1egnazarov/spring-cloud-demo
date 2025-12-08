package ru.noleg.orderservice.event;

import java.math.BigDecimal;

public record OrderCreatedEvent(Long orderId, Long userId, BigDecimal amount) {}
