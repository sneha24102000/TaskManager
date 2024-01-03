package com.nikozka.app.controllers;

import com.nikozka.app.dtos.RequestCreateTaskDto;
import com.nikozka.app.dtos.StatusDto;
import com.nikozka.app.dtos.TaskDto;
import com.nikozka.app.servises.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor

public class TaskController {

    private final TaskService taskService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Long> createTask(@RequestBody @Valid RequestCreateTaskDto task) {
        Long id = taskService.createTask(task);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/current")
    public ResponseEntity<List<TaskDto>> getMyTasks(Pageable pageable) {
        List<TaskDto> myTasks = taskService.getTasksForUser(pageable);
        return new ResponseEntity<>(myTasks, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId) {
        TaskDto task = taskService.getTaskById(taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{taskId}/status")
    public ResponseEntity<Void> updateTaskStatus(@PathVariable Long taskId, @RequestBody @Valid StatusDto newStatus) {
        taskService.updateTaskStatus(taskId, newStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(Pageable pageable) {
        List<TaskDto> tasks = taskService.getAllTasks(pageable);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}