package com.example.todo_rest.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.todo_rest.user.dto.NewUserCommand;
import com.example.todo_rest.user.model.User;
import com.example.todo_rest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(NewUserCommand newUserCommand) {
        User user = User.builder().username(newUserCommand.username())
                .password(passwordEncoder.encode(newUserCommand.password()))
                .email(newUserCommand.email()).isAdmin(false).build();
        return userRepository.save(user);
    }
}
