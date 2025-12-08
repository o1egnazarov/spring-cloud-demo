package ru.noleg.paymentservice.event.producer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import ru.noleg.paymentservice.entity.PaymentStatus;
import ru.noleg.paymentservice.event.PaymentCompletedEvent;

@Component
@RequiredArgsConstructor
public class PaymentEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(PaymentEventProducer.class);

    private final StreamBridge streamBridge;

    /**
    * Пишет в payment-completed
    * */
    public void sendPaymentCompleted(Long orderId, PaymentStatus paymentStatus) {
        PaymentCompletedEvent event = new PaymentCompletedEvent(orderId, paymentStatus);

        streamBridge.send("paymentCompleted-out-0", event);
        logger.info("Sent PaymentCompleted event: {}", event);
    }
}
