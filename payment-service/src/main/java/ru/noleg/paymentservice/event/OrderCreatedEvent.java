package ru.noleg.paymentservice.event;

import java.math.BigDecimal;

public record OrderCreatedEvent(Long orderId, Long userId, BigDecimal amount) {}