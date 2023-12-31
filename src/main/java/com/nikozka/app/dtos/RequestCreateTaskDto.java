package com.nikozka.app.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RequestCreateTaskDto {

    private String description;
    private LocalDateTime dueDate;

    public RequestCreateTaskDto() {
    }

    public RequestCreateTaskDto(String description, LocalDateTime dueDate) {
        this.description = description;
        this.dueDate = dueDate;
    }
}


