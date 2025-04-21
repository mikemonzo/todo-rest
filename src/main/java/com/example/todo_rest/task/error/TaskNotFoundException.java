package com.example.todo_rest.task.error;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException() {
        super("Task not found");
    }

    public TaskNotFoundException(String title) {
        super(title);
    }

    public TaskNotFoundException(Long id) {
        super("Could not find task ID: ".formatted(id));
    }
}
