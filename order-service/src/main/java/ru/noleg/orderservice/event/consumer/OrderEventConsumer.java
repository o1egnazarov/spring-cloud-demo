package ru.noleg.orderservice.event.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.noleg.orderservice.entity.Order;
import ru.noleg.orderservice.entity.PaymentStatus;
import ru.noleg.orderservice.event.PaymentCompletedEvent;
import ru.noleg.orderservice.repository.OrderRepository;
import ru.noleg.orderservice.service.OrderService;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    /**
     * Слушает payment-completed
     */
    @KafkaListener(topics = "payment-completed", groupId = "order-service")
    public void paymentCompleted(@Payload PaymentCompletedEvent event) {
        log.info("Received PaymentCompletedEvent: {}", event);

        Order order = orderService.getOrderById(event.orderId());

        if (order.getPaymentStatus() == PaymentStatus.PAID) {
            System.out.println(order.getPaymentStatus() + "PAID");
            log.info("Order already paid: {}", order.getPaymentStatus());
            return;
        }

        if (event.status() == PaymentStatus.PAID) {
            order.setPaymentStatus(PaymentStatus.PAID);
            log.info("Order paid: {}", order.getPaymentStatus());
        } else {
            order.setPaymentStatus(PaymentStatus.PAYMENT_FAILED);
            log.info("Order fail: {}", order.getPaymentStatus());

        }

        orderRepository.save(order);
        log.info("Payment finished for orderId: {} with status {}",
                event.orderId(),
                event.status());
    }
}
