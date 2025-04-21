package com.example.todo_rest.task.dto;

import java.time.LocalDateTime;
import com.example.todo_rest.task.model.Task;
import com.example.todo_rest.user.dto.NewUserResponse;

public record GetTaskDto(Long id, String title, String description, LocalDateTime createdAt,
        LocalDateTime deadline, NewUserResponse author) {

    public static GetTaskDto of(Task task) {
        return new GetTaskDto(task.getId(), task.getTitle(), task.getDescription(),
                task.getCreatedAt(), task.getDeadline(), NewUserResponse.of(task.getAuthor()));
    }
}
