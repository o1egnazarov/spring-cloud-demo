package ru.noleg.paymentservice.event.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.noleg.paymentservice.entity.PaymentStatus;
import ru.noleg.paymentservice.event.OrderCreatedEvent;
import ru.noleg.paymentservice.event.producer.PaymentEventProducer;
import ru.noleg.paymentservice.service.PaymentService;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final PaymentService paymentService;
    private final PaymentEventProducer paymentEventProducer;

    /**
     * Слушает order.created
     */
    @KafkaListener(topics = "order-created", groupId = "payment-service")
    public void orderCreated(@Payload OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent: {}", event);

        PaymentStatus paymentStatus = paymentService.processPayment(
                event.orderId(), event.userId(), event.amount()
        );

        paymentEventProducer.sendPaymentCompleted(event.orderId(), paymentStatus);

        log.info("Payment finished for orderId: {} with status {}",
                event.orderId(),
                paymentStatus);
    }
}