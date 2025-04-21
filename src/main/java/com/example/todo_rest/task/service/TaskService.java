package com.example.todo_rest.task.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.todo_rest.task.dto.EditTaskCommand;
import com.example.todo_rest.task.error.TaskNotFoundException;
import com.example.todo_rest.task.model.Task;
import com.example.todo_rest.task.repository.TaskRepository;
import com.example.todo_rest.user.model.User;
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

    public List<Task> findByAuthor(User author) {
        List<Task> tasks = taskRepository.findByAuthor(author);

        if (tasks.isEmpty()) {
            throw new TaskNotFoundException();
        }

        return tasks;
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task save(EditTaskCommand editTaskCommand, User author) {
        return taskRepository.save(Task.builder().title(editTaskCommand.title())
                .description(editTaskCommand.description()).deadline(editTaskCommand.deadline())
                .author(author).build());
    }

    public Task edit(EditTaskCommand editTaskCommand, Long id) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(editTaskCommand.title());
            task.setDescription(editTaskCommand.description());
            task.setDeadline(editTaskCommand.deadline());
            return taskRepository.save(task);
        }).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
