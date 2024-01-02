package com.nikozka.app.utils;

import com.nikozka.app.dtos.TaskStatus;
import com.nikozka.app.entity.TaskEntity;

@FunctionalInterface
public interface TaskStatusValidator {
    boolean isTaskStatusValid(TaskEntity task, TaskStatus status);
}
