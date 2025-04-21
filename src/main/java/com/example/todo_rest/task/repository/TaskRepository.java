package com.example.todo_rest.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.todo_rest.task.model.Task;
import java.util.List;
import com.example.todo_rest.user.model.User;


public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAuthor(User author);
}
