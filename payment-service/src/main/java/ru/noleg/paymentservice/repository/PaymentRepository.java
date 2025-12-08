package ru.noleg.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.noleg.paymentservice.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
