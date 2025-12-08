package ru.noleg.orderservice.event;

import ru.noleg.orderservice.entity.PaymentStatus;

public record PaymentCompletedEvent(Long orderId, PaymentStatus status) {}
