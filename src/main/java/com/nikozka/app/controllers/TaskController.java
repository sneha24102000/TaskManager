package com.nikozka.app.controllers;

import com.nikozka.app.dtos.IdDto;
import com.nikozka.app.dtos.RequestCreateTaskDto;
import com.nikozka.app.dtos.StatusDto;
import com.nikozka.app.dtos.TaskDto;
import com.nikozka.app.servises.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Controller", description = "Operations related to task management")
public class TaskController {

    private final TaskService taskService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new task", description = "Create a new task with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = IdDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, validation error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, I don’t recognize you",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))

    })
    public ResponseEntity<IdDto> createTask(@RequestBody @Valid RequestCreateTaskDto task) {
        IdDto idDto = taskService.createTask(task);
        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/current")
    @Operation(summary = "Get tasks for the current user", description = "Retrieve tasks assigned to the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class, type = "List"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, I don’t recognize you",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))

    })
    public ResponseEntity<List<TaskDto>> getUsersTasks(Pageable pageable) {
        List<TaskDto> myTasks = taskService.getTasksForUser(pageable);
        return new ResponseEntity<>(myTasks, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{taskId}")
    @Operation(summary = "Get task by ID", description = "Retrieve a task by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, I don’t recognize you",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId) {
        TaskDto task = taskService.getTaskById(taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{taskId}/status")
    @Operation(summary = "Update task status", description = "Update the status of a task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, validation error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, I don’t recognize you",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))

    })
    public ResponseEntity<Void> updateTaskStatus(@PathVariable Long taskId, @RequestBody @Valid StatusDto newStatus) {
        taskService.updateTaskStatus(taskId, newStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all tasks", description = "Retrieve all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class, type = "List"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, I don’t recognize you",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<List<TaskDto>> getAllTasks(Pageable pageable) {
        List<TaskDto> tasks = taskService.getAllTasks(pageable);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete task", description = "Delete a task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, I don’t recognize you",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden, I know who you are, but you're not allowed here",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}