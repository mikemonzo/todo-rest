package com.example.todo_rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todo_rest.dto.NewTaskCommand;
import com.example.todo_rest.model.Task;
import com.example.todo_rest.service.TaskService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<Task> getAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Task getById(@RequestParam Long id) {
        return taskService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody NewTaskCommand newTaskCommand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(newTaskCommand));
    }

}
