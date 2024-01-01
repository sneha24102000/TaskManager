package com.nikozka.app.dtos;

import com.nikozka.app.exceptions.StatusNotExistException;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum TaskStatus {
    PLANNED,
    WORK_IN_PROGRESS,
    POSTPONED,
    NOTIFIED,
    SIGNED,
    DONE,
    CANCELLED;
    private static final Map<String, TaskStatus> BY_NAME_LOWER_CASE =
            Arrays.stream(TaskStatus.values())
                    .collect(
                            Collectors
                                    .toMap(enumExample -> enumExample.name().toLowerCase(), Function.identity()));

    public static TaskStatus valueOfIgnoreCase(String name) {
        var result = BY_NAME_LOWER_CASE.get(name.toLowerCase());
        if (result != null) {
            return result;
        }
        throw new StatusNotExistException("Status name does not exist");
    }
}
