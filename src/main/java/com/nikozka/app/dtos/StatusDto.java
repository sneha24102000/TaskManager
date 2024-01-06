package com.nikozka.app.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
        @NotNull(message = "status.newStatus.notNull")
        @Pattern(regexp = "(?i)PLANNED|WORK_IN_PROGRESS|POSTPONED|NOTIFIED|SIGNED|DONE|CANCELLED",
                message = "status.newStatus.invalid")
        private String newStatus;
}
