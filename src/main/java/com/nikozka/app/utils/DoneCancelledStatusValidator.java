package com.nikozka.app.utils;

import com.nikozka.app.dtos.TaskStatus;
import com.nikozka.app.entity.TaskEntity;

public class DoneCancelledStatusValidator implements TaskStatusValidator {
    @Override
    public boolean isTaskStatusValid(TaskEntity task, TaskStatus status) {
        return false;
    }
}
