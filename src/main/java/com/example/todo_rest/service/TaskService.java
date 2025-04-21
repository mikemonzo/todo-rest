package com.example.todo_rest.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.todo_rest.dto.NewTaskCommand;
import com.example.todo_rest.error.TaskNotFoundException;
import com.example.todo_rest.model.Task;
import com.example.todo_rest.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> findAll() {
        List<Task> tasks = taskRepository.findAll();

        if (tasks.isEmpty()) {
            throw new TaskNotFoundException();
        }

        return tasks;
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task save(NewTaskCommand newTaskCommand) {
        return taskRepository.save(Task.builder().title(newTaskCommand.title())
                .description(newTaskCommand.description()).deadline(newTaskCommand.deadline())
                .build());
    }
}
