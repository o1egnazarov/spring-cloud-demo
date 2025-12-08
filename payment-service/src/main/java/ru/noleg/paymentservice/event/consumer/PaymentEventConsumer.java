package ru.noleg.paymentservice.event.consumer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.noleg.paymentservice.entity.PaymentStatus;
import ru.noleg.paymentservice.event.OrderCreatedEvent;
import ru.noleg.paymentservice.event.producer.PaymentEventProducer;
import ru.noleg.paymentservice.service.PaymentService;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final static Logger logger = LoggerFactory.getLogger(PaymentEventConsumer.class);

    private final PaymentService paymentService;
    private final PaymentEventProducer paymentEventProducer;

    /**
     * Слушает order.created
     */
    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return event -> {
            logger.info("Received OrderCreatedEvent: {}", event);

            PaymentStatus paymentStatus = paymentService.processPayment(
                    event.orderId(), event.userId(), event.amount()
            );

            paymentEventProducer.sendPaymentCompleted(event.orderId(), paymentStatus);

            logger.info("Payment finished for orderId: {} with status {}",
                    event.orderId(),
                    paymentStatus);
        };
    }
}