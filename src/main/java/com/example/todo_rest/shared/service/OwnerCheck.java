package com.example.todo_rest.shared.service;

import org.springframework.stereotype.Component;
import com.example.todo_rest.task.model.Task;
import com.example.todo_rest.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OwnerCheck {

    private final TaskRepository taskRepository;

    public boolean check(Task task, Long userId) {
        if (task != null && task.getAuthor() != null) {
            return task.getAuthor().getId().equals(userId);
        } else {
            return false;
        }
    }

    public boolean check(Long taskId, Long userId) {
        return taskRepository.findById(taskId).map(task -> task.getAuthor().getId().equals(userId))
                .orElse(false);
    }
}
