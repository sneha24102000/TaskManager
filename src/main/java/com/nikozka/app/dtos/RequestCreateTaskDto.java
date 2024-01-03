package com.nikozka.app.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
public class RequestCreateTaskDto {
    @NotNull
    @Size(min = 5, max = 255, message = "Description name must be between 5 and 255 characters")
    private String description;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public RequestCreateTaskDto() {
    }

    public RequestCreateTaskDto(String description, LocalDate date) {
        this.description = description;
        this.date = date;
    }
}


