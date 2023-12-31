package com.nikozka.app.servises;

import com.nikozka.app.dtos.RequestCreateTaskDto;
import com.nikozka.app.dtos.TaskDto;
import com.nikozka.app.dtos.TaskStatus;
import com.nikozka.app.entity.TaskEntity;
import com.nikozka.app.exceptions.InvalidStateException;
import com.nikozka.app.exceptions.TaskNotFoundException;
import com.nikozka.app.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId)));
    }

    public void updateTaskStatus(Long id, TaskStatus status) {
        TaskDto task = getTaskById(id);

        if (task.getStatus() == TaskStatus.DONE ||
                task.getStatus() == TaskStatus.CANCELLED) {
            throw new InvalidStateException("Task is already in final state");
        }
        if (task.getStatus() == TaskStatus.PLANNED
                &&
                status == TaskStatus.WORK_IN_PROGRESS ||
                status == TaskStatus.POSTPONED ||
                status == TaskStatus.CANCELLED ) {

            taskRepository.save(convertToEntity(task));
        }
        if (task.getStatus() == TaskStatus.WORK_IN_PROGRESS || task.getStatus() == TaskStatus.POSTPONED
                &&
                status == TaskStatus.NOTIFIED ||
                status == TaskStatus.SIGNED ||
                status == TaskStatus.CANCELLED ) {

            taskRepository.save(convertToEntity(task));
        }
        if (task.getStatus() == TaskStatus.NOTIFIED || task.getStatus() == TaskStatus.SIGNED
                &&
                status == TaskStatus.DONE ||
                status == TaskStatus.CANCELLED ) {

            taskRepository.save(convertToEntity(task));
        }

        throw new InvalidStateException("Invalid state transition for task with id: " + task.getId());
    }

    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll()
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