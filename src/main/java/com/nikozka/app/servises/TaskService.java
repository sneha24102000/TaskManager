package com.nikozka.app.servises;

import com.nikozka.app.dtos.RequestCreateTaskDto;
import com.nikozka.app.dtos.StatusDto;
import com.nikozka.app.dtos.TaskDto;
import com.nikozka.app.dtos.TaskStatus;
import com.nikozka.app.entity.TaskEntity;
import com.nikozka.app.exceptions.InvalidStateException;
import com.nikozka.app.exceptions.TaskNotFoundException;
import com.nikozka.app.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final ModelMapper modelMapper;

    public Long createTask(RequestCreateTaskDto task) {
        TaskEntity taskEntity = convertToEntity(task);
        taskEntity.setStatus(TaskStatus.PLANNED);
        TaskEntity saved = taskRepository.save(taskEntity);
        return saved.getId();
    }

    public TaskDto getTaskById(Long taskId) {
        return convertToTaskDTO(taskRepository.findById(taskId)
                .orElseThrow());
    }

    public void updateTaskStatus(Long id, StatusDto statusDto) {

        TaskEntity task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
        TaskStatus status = task.getStatus();

        TaskStatus newStatus = TaskStatus.valueOfIgnoreCase(statusDto.getNewStatus());
        task.setStatus(newStatus);

        if (status == TaskStatus.DONE ||
                status == TaskStatus.CANCELLED) {
            throw new InvalidStateException("Task is already in final state");
        }
        if (status == TaskStatus.PLANNED
                &&
                newStatus == TaskStatus.WORK_IN_PROGRESS ||
                newStatus == TaskStatus.POSTPONED ||
                newStatus == TaskStatus.CANCELLED) {

            taskRepository.save(task);
            return;
        }
        if (status == TaskStatus.WORK_IN_PROGRESS || status == TaskStatus.POSTPONED
                &&
                newStatus == TaskStatus.NOTIFIED ||
                newStatus == TaskStatus.SIGNED ||
                newStatus == TaskStatus.CANCELLED) {

            taskRepository.save(task);
            return;
        }
        if (status == TaskStatus.NOTIFIED || status == TaskStatus.SIGNED
                &&
                newStatus == TaskStatus.DONE ||
                newStatus == TaskStatus.CANCELLED) {

            taskRepository.save(task);
            return;
        }
        throw new InvalidStateException("Invalid state transition for task with id: " + task.getId());
    }

    public List<TaskDto> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .stream()
                .map(this::convertToTaskDTO)
                .toList();
    }

    public void deleteTask(Long taskId) {
        TaskDto task = getTaskById(taskId);
        taskRepository.delete(convertToEntity(task));
    }

    private TaskDto convertToTaskDTO(TaskEntity task) {
        return modelMapper.map(task, TaskDto.class);
    }

    private TaskEntity convertToEntity(RequestCreateTaskDto task) {
        return modelMapper.map(task, TaskEntity.class);
    }

    private TaskEntity convertToEntity(TaskDto task) {
        return modelMapper.map(task, TaskEntity.class);
    }
}