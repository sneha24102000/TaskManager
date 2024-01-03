package com.nikozka.app.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDto {
        private String id;
        private String description;
        private LocalDate date;
        private TaskStatus status;
}
