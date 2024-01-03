package com.nikozka.app.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusDto {
        @NotNull(message = "New status is required")
        @Pattern(regexp = "(?i)PLANNED|WORK_IN_PROGRESS|POSTPONED|NOTIFIED|SIGNED|DONE|CANCELLED",
                message = "Invalid status. Allowed values are PLANNED, WORK_IN_PROGRESS, POSTPONED, NOTIFIED, SIGNED, DONE, CANCELLED")
        private String newStatus;
}
