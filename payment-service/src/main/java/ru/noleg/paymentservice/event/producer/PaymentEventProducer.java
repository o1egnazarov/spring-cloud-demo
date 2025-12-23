package ru.noleg.paymentservice.event.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.noleg.paymentservice.entity.PaymentStatus;
import ru.noleg.paymentservice.event.PaymentCompletedEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate;

    /**
     * Пишет в payment-completed
     *
     */
    public void sendPaymentCompleted(Long orderId, PaymentStatus paymentStatus) {
        PaymentCompletedEvent event = new PaymentCompletedEvent(orderId, paymentStatus);

        kafkaTemplate.send("payment-completed", event);
        log.info("Sent PaymentCompleted event: {}", event);
    }
}
