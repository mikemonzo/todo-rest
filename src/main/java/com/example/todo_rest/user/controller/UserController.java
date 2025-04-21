package com.example.todo_rest.user.controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.todo_rest.user.dto.NewUserCommand;
import com.example.todo_rest.user.dto.NewUserResponse;
import com.example.todo_rest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<NewUserResponse> createUser(@RequestBody NewUserCommand newUserCommand) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(NewUserResponse.of(userService.register(newUserCommand)));
    }

}
