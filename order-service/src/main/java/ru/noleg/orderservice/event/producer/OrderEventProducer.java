package ru.noleg.orderservice.event.producer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import ru.noleg.orderservice.event.OrderCreatedEvent;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventProducer.class);

    private final StreamBridge streamBridge;

    /**
     * Пишем в order.created
     */
    public void sendOrderCreated(OrderCreatedEvent createdEvent) {
        streamBridge.send("orderCreated-out-0", createdEvent);

        logger.info("Order created with id: {}, for userId: {}",
                createdEvent.orderId(),
                createdEvent.userId()
        );
    }
}
