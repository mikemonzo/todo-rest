package com.example.todo_rest.task.model;

import java.time.LocalDateTime;
import com.example.todo_rest.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private String title;
    @Lob
    private String description;
    private LocalDateTime deadline;

    @ManyToOne
    private User author;

}
