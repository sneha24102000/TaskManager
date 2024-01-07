package com.nikozka.app.servises;

import com.nikozka.app.dtos.RequestCreateTaskDto;
import com.nikozka.app.dtos.StatusDto;
import com.nikozka.app.dtos.TaskDto;
import com.nikozka.app.dtos.TaskStatus;
import com.nikozka.app.entity.TaskEntity;
import com.nikozka.app.entity.UserEntity;
import com.nikozka.app.exceptions.InvalidStateException;
import com.nikozka.app.exceptions.TaskNotFoundException;
import com.nikozka.app.repositories.TaskRepository;
import com.nikozka.app.repositories.UserRepository;
import com.nikozka.app.utils.TaskStatusValidator;
import com.nikozka.app.utils.TaskStatusValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final TaskStatusValidatorFactory factory;
    private final MessageSource messageSource;


    public List<TaskDto> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .stream()
                .map(this::convertToTaskDTO)
                .toList();
    }

    public TaskDto getTaskById(Long taskId) {
        return convertToTaskDTO(findTaskById(taskId));
    }

    public List<TaskDto> getTasksForUser(Pageable pageable) {
        return taskRepository.findByUserId(getUserId(), pageable)
                .stream()
                .map(this::convertToTaskDTO)
                .toList();
    }

    public Long createTask(RequestCreateTaskDto task) {
        TaskEntity taskEntity = convertToEntity(task);
        taskEntity.setStatus(TaskStatus.PLANNED);
        taskEntity.setUserId(getUserId());
        return taskRepository.save(taskEntity).getId();
    }

    public void updateTaskStatus(Long taskId, StatusDto statusDto) {
        TaskEntity task = findTaskById(taskId);
        TaskStatus previousStatus = task.getStatus();

        TaskStatus newStatus = TaskStatus.valueOfIgnoreCase(statusDto.getNewStatus());

        TaskStatusValidator updater = factory.getValidator(previousStatus);

        if (!updater.isTaskStatusValid(task, newStatus)) {
            throw new InvalidStateException(messageSource.getMessage(
                    "task.invalid.state.transition", new Object[]{taskId}, LocaleContextHolder.getLocale()));
        }
        task.setStatus(newStatus);
        taskRepository.save(task);
    }


    public void deleteTask(Long taskId) {
        taskRepository.delete(findTaskById(taskId));
    }

    private TaskDto convertToTaskDTO(TaskEntity task) {
        return modelMapper.map(task, TaskDto.class);
    }

    private TaskEntity convertToEntity(RequestCreateTaskDto task) {
        return modelMapper.map(task, TaskEntity.class);
    }

    private Long getUserId() {
        UserEntity user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.getId();
    }

    private TaskEntity findTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(messageSource.getMessage(
                        "task.not.found", new Object[]{taskId}, LocaleContextHolder.getLocale())));
    }
}
