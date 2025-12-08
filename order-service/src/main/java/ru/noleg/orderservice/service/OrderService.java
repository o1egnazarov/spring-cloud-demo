package ru.noleg.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noleg.orderservice.client.UserClient;
import ru.noleg.orderservice.dto.OrderCreatedRequest;
import ru.noleg.orderservice.dto.UserResponse;
import ru.noleg.orderservice.entity.Order;
import ru.noleg.orderservice.entity.PaymentStatus;
import ru.noleg.orderservice.event.OrderCreatedEvent;
import ru.noleg.orderservice.event.producer.OrderEventProducer;
import ru.noleg.orderservice.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final static Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final OrderEventProducer orderProducer;

    @Transactional
    // TODO: ADD TRANSACTIONAL OUTBOX
    public Order createOrder(OrderCreatedRequest orderCreatedRequest) {
        UserResponse user = userClient.getUser(orderCreatedRequest.userId()).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );

        Order order = new Order();
        order.setUserId(user.id());
        order.setProduct(orderCreatedRequest.product());
        order.setAmount(orderCreatedRequest.amount());
        order.setPaymentStatus(PaymentStatus.IN_PROGRESS);

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(
                savedOrder.getId(), savedOrder.getUserId(), savedOrder.getAmount()
        );

        orderProducer.sendOrderCreated(orderCreatedEvent);
        logger.info("Sent orderCreateEvent for orderId: {}", orderCreatedEvent.orderId());

        return savedOrder;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Order does not exist")
        );
    }
}
