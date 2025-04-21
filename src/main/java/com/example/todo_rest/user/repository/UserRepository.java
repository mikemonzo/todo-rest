package com.example.todo_rest.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.todo_rest.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByUsername(String username);
}
