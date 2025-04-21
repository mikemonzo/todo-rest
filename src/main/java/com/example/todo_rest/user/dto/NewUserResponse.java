package com.example.todo_rest.user.dto;

import com.example.todo_rest.user.model.User;

public record NewUserResponse(Long id, String username, String email) {

    public static NewUserResponse of(User user) {
        return new NewUserResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
