package com.example.todo_rest.service;

import org.springframework.stereotype.Service;
import com.example.todo_rest.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
}
