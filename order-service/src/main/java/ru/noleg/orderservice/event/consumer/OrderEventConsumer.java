package ru.noleg.orderservice.event.consumer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.noleg.orderservice.entity.Order;
import ru.noleg.orderservice.entity.PaymentStatus;
import ru.noleg.orderservice.event.PaymentCompletedEvent;
import ru.noleg.orderservice.repository.OrderRepository;
import ru.noleg.orderservice.service.OrderService;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class OrderEventConsumer {
    private final static Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class);

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    /**
     * Слушает payment.completed
     */
    @Bean
    public Consumer<PaymentCompletedEvent> paymentCompleted() {
        return event -> {
            logger.info("Received PaymentCompletedEvent: {}", event);

            Order order = orderService.getOrderById(event.orderId());

            if (order.getPaymentStatus() == PaymentStatus.PAID) {
                System.out.println(order.getPaymentStatus() + "PAID");
                logger.info("Order already paid: {}", order.getPaymentStatus());
                return;
            }

            if (event.status() == PaymentStatus.PAID) {
                order.setPaymentStatus(PaymentStatus.PAID);
                logger.info("Order paid: {}", order.getPaymentStatus());
            } else {
                order.setPaymentStatus(PaymentStatus.PAYMENT_FAILED);
                logger.info("Order fail: {}", order.getPaymentStatus());

            }

            orderRepository.save(order);

            logger.info("Payment finished for orderId: {} with status {}",
                    event.orderId(),
                    event.status());
        };
    }
}
