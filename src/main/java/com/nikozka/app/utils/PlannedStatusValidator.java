package com.nikozka.app.utils;

import com.nikozka.app.dtos.TaskStatus;
import com.nikozka.app.entity.TaskEntity;

public class PlannedStatusValidator implements TaskStatusValidator {
    @Override
    public boolean isTaskStatusValid(TaskEntity task, TaskStatus newStatus) {
        return newStatus == TaskStatus.WORK_IN_PROGRESS ||
                newStatus == TaskStatus.POSTPONED ||
                newStatus == TaskStatus.CANCELLED;
    }
}
