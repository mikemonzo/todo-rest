package com.example.todo_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.todo_rest.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
