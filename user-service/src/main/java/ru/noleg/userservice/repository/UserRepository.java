package ru.noleg.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.noleg.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
