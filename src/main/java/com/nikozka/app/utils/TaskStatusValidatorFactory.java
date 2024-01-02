package com.nikozka.app.utils;

import com.nikozka.app.dtos.TaskStatus;
import com.nikozka.app.entity.TaskEntity;

import java.util.Map;

public class TaskStatusValidatorFactory {
    private final Map<TaskStatus, TaskStatusValidator> updaterMap = Map.of(
            TaskStatus.PLANNED, TaskStatusValidatorFactory::isPlannedStatusValid,
            TaskStatus.WORK_IN_PROGRESS,  TaskStatusValidatorFactory::isWorkInProgressPostponedValid,
            TaskStatus.POSTPONED, TaskStatusValidatorFactory::isWorkInProgressPostponedValid,
            TaskStatus.NOTIFIED, TaskStatusValidatorFactory::isNotifiedSignedStatusValid,
            TaskStatus.SIGNED,  TaskStatusValidatorFactory::isNotifiedSignedStatusValid,
            TaskStatus.DONE,  (task, status) -> false,
            TaskStatus.CANCELLED, ((task, status) -> false));

    public TaskStatusValidator getValidator(TaskStatus status) {
        return updaterMap.get(status);
    }

    private static boolean isWorkInProgressPostponedValid(TaskEntity task, TaskStatus status) {
        return status == TaskStatus.NOTIFIED
                || status == TaskStatus.SIGNED
                || status == TaskStatus.CANCELLED;
    }

    private static boolean isNotifiedSignedStatusValid(TaskEntity task, TaskStatus status) {
        return status == TaskStatus.DONE || status == TaskStatus.CANCELLED;
    }

    private static boolean isPlannedStatusValid(TaskEntity task, TaskStatus status) {
        return status == TaskStatus.WORK_IN_PROGRESS
                || status == TaskStatus.POSTPONED
                || status == TaskStatus.CANCELLED;
    }
}
