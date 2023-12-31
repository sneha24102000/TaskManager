package com.nikozka.app.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RequestUpdateStatusDto {

    private Long id;
    private TaskStatus status;

    public RequestUpdateStatusDto() {
    }

    public RequestUpdateStatusDto(Long id, TaskStatus status) {
        this.id = id;
        this.status = status;
    }
}


