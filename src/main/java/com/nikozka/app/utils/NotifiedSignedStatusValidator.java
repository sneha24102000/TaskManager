package com.nikozka.app.utils;

import com.nikozka.app.dtos.TaskStatus;
import com.nikozka.app.entity.TaskEntity;

public class NotifiedSignedStatusValidator implements TaskStatusValidator {
    @Override
    public boolean isTaskStatusValid(TaskEntity task, TaskStatus newStatus) {
        return newStatus == TaskStatus.DONE || newStatus == TaskStatus.CANCELLED;
    }
}
