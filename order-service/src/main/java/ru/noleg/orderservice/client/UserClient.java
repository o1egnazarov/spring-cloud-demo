package ru.noleg.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.noleg.orderservice.dto.UserResponse;

import java.util.Optional;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/users/{id}")
    Optional<UserResponse> getUser(@PathVariable("id") Long id);
}
