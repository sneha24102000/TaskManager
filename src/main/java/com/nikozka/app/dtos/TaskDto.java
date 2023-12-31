package com.nikozka.app.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
public class TaskDto {
        private String id;
        private String description;
        private LocalDateTime dueDate;
        @Setter
        private TaskStatus status;

}
