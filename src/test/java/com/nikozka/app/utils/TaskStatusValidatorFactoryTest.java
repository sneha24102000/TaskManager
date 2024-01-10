package com.nikozka.app.utils;

import com.nikozka.app.dtos.TaskStatus;
import com.nikozka.app.entity.TaskEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskStatusValidatorFactoryTest {

    @Test
    void getValidator_PlannedStatus_ReturnsCorrectValidator() {
        TaskStatusValidatorFactory factory = new TaskStatusValidatorFactory();
        TaskStatusValidator validator = factory.getValidator(TaskStatus.PLANNED);
        assertAll("PLANNED Validator",
                () -> assertNotNull(validator, "Validator should not be null"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.PLANNED),
                        "PLANNED should not be valid"),
                () -> assertTrue(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.WORK_IN_PROGRESS),
                        "WORK_IN_PROGRESS should be valid"),
                () -> assertTrue(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.POSTPONED),
                        "POSTPONED should be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.NOTIFIED),
                        "NOTIFIED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.SIGNED),
                        "SIGNED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.DONE),
                        "DONE should not be valid"),
                () -> assertTrue(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.CANCELLED),
                        "CANCELLED should be valid")
        );
    }

    @Test
    void getValidator_WorkInProgressStatus_ReturnsCorrectValidator() {
        TaskStatusValidatorFactory factory = new TaskStatusValidatorFactory();
        TaskStatusValidator validator = factory.getValidator(TaskStatus.WORK_IN_PROGRESS);
        assertAll("WORK_IN_PROGRESS Validator",
                () -> assertNotNull(validator, "Validator should not be null"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.PLANNED),
                        "PLANNED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.WORK_IN_PROGRESS),
                        "WORK_IN_PROGRESS should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.POSTPONED),
                        "POSTPONED should not be valid"),
                () -> assertTrue(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.NOTIFIED),
                        "NOTIFIED should be valid"),
                () -> assertTrue(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.SIGNED),
                        "SIGNED should be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.DONE),
                        "DONE should not be valid"),
                () -> assertTrue(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.CANCELLED),
                        "CANCELLED should be valid")
        );
    }
    @Test
    void getValidator_PostponedStatus_ReturnsCorrectValidator() {
        TaskStatusValidatorFactory factory = new TaskStatusValidatorFactory();
        TaskStatusValidator validator = factory.getValidator(TaskStatus.POSTPONED);
        assertAll("POSTPONED Validator",
                () -> assertNotNull(validator, "Validator should not be null"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.PLANNED),
                        "PLANNED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.WORK_IN_PROGRESS),
                        "WORK_IN_PROGRESS should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.POSTPONED),
                        "POSTPONED should not be valid"),
                () -> assertTrue(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.NOTIFIED),
                        "NOTIFIED should be valid"),
                () -> assertTrue(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.SIGNED),
                        "SIGNED should be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.DONE),
                        "DONE should not be valid"),
                () -> assertTrue(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.CANCELLED),
                        "CANCELLED should be valid")
        );
    }


    @Test
    void getValidator_NotifiedStatus_ReturnsCorrectValidator() {
        TaskStatusValidatorFactory factory = new TaskStatusValidatorFactory();
        TaskStatusValidator validator = factory.getValidator(TaskStatus.NOTIFIED);

        assertAll("NOTIFIED Validator",
                () -> assertNotNull(validator, "Validator should not be null"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.PLANNED),
                        "PLANNED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.WORK_IN_PROGRESS),
                        "WORK_IN_PROGRESS should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.POSTPONED),
                        "POSTPONED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.NOTIFIED),
                        "NOTIFIED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.SIGNED),
                        "SIGNED should not be valid"),
                () -> assertTrue(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.DONE),
                        "DONE should be valid"),
                () -> assertTrue(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.CANCELLED),
                        "CANCELLED should be valid")
        );
    }
    @Test
    void getValidator_SignedStatus_ReturnsCorrectValidator() {
        TaskStatusValidatorFactory factory = new TaskStatusValidatorFactory();
        TaskStatusValidator validator = factory.getValidator(TaskStatus.SIGNED);

        assertAll("SIGNED Validator",
                () -> assertNotNull(validator, "Validator should not be null"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.PLANNED),
                        "PLANNED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.WORK_IN_PROGRESS),
                        "WORK_IN_PROGRESS should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.POSTPONED),
                        "POSTPONED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.NOTIFIED),
                        "NOTIFIED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.SIGNED),
                        "SIGNED should not be valid"),
                () -> assertTrue(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.DONE),
                        "DONE should be valid"),
                () -> assertTrue(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.CANCELLED),
                        "CANCELLED should be valid")
        );
    }
    @Test
    void getValidator_CancelledStatus_ReturnsCorrectValidator() {
        TaskStatusValidatorFactory factory = new TaskStatusValidatorFactory();
        TaskStatusValidator validator = factory.getValidator(TaskStatus.CANCELLED);

        assertAll("CANCELLED Validator",
                () -> assertNotNull(validator, "Validator should not be null"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.PLANNED),
                        "PLANNED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.WORK_IN_PROGRESS),
                        "WORK_IN_PROGRESS should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.POSTPONED),
                        "POSTPONED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.NOTIFIED),
                        "NOTIFIED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.SIGNED),
                        "SIGNED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.DONE),
                        "DONE should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.CANCELLED),
                        "CANCELLED should not be valid")
        );
    }
    @Test
    void getValidator_DoneStatus_ReturnsCorrectValidator() {
        TaskStatusValidatorFactory factory = new TaskStatusValidatorFactory();
        TaskStatusValidator validator = factory.getValidator(TaskStatus.DONE);

        assertAll("DONE Validator",
                () -> assertNotNull(validator, "Validator should not be null"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.PLANNED),
                        "PLANNED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.WORK_IN_PROGRESS),
                        "WORK_IN_PROGRESS should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.POSTPONED),
                        "POSTPONED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.NOTIFIED),
                        "NOTIFIED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.SIGNED),
                        "SIGNED should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.DONE),
                        "DONE should not be valid"),
                () -> assertFalse(validator.isTaskStatusValid(new TaskEntity(), TaskStatus.CANCELLED),
                        "CANCELLED should not be valid")
        );
    }
}
