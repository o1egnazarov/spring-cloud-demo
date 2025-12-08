package ru.noleg.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.noleg.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
