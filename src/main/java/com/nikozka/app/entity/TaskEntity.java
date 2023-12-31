package com.nikozka.app.entity;

import com.nikozka.app.dtos.TaskStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String description;
    @Setter
    private LocalDateTime dueDate;
    @Setter
    private TaskStatus status;

    public TaskEntity() {
    }

    public TaskEntity(Long id, String description, LocalDateTime dueDate, TaskStatus status) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }
}
