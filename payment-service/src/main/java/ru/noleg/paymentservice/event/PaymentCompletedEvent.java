package ru.noleg.paymentservice.event;

import ru.noleg.paymentservice.entity.PaymentStatus;

public record PaymentCompletedEvent(Long orderId, PaymentStatus status) {}
