package com.nikozka.app.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class TaskDto {
        private String id;
        private String description;
        private LocalDateTime dueDate;
        private TaskStatus status;
}
