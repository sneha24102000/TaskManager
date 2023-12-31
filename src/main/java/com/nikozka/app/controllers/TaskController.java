package com.nikozka.app.controllers;

import com.nikozka.app.dtos.RequestCreateTaskDto;
import com.nikozka.app.dtos.TaskDto;
import com.nikozka.app.dtos.TaskStatus;
import com.nikozka.app.servises.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Long> createTask(@RequestBody RequestCreateTaskDto task) {
        Long id = taskService.createTask(task);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId) {
        TaskDto task = taskService.getTaskById(taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{taskId}/status/{newStatus}")
    public ResponseEntity<Void> updateTaskStatus(@PathVariable Long taskId, @PathVariable TaskStatus newStatus) {
        taskService.updateTaskStatus(taskId, newStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(Pageable pageable)  {
        List<TaskDto> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}