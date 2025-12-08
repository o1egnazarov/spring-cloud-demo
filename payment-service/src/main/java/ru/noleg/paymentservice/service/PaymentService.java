package ru.noleg.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noleg.paymentservice.entity.Payment;
import ru.noleg.paymentservice.entity.PaymentStatus;
import ru.noleg.paymentservice.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentStatus processPayment(Long orderId, Long userId, BigDecimal amount) {
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setOrderId(orderId);
        payment.setAmount(amount);

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        PaymentStatus paymentStatus = new Random().nextBoolean() ?
                PaymentStatus.PAID :
                PaymentStatus.PAYMENT_FAILED;

        paymentRepository.save(payment);

        return paymentStatus;
    }
}
