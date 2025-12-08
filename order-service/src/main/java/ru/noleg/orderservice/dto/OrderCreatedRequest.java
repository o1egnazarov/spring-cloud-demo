package ru.noleg.orderservice.dto;

import java.math.BigDecimal;

public record OrderCreatedRequest(Long userId, String product, BigDecimal amount) {
}
