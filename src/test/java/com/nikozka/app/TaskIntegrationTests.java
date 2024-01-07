package com.nikozka.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikozka.app.dtos.RequestCreateTaskDto;
import com.nikozka.app.dtos.StatusDto;
import com.nikozka.app.dtos.TaskStatus;
import com.nikozka.app.entity.TaskEntity;
import com.nikozka.app.entity.UserEntity;
import com.nikozka.app.repositories.TaskRepository;
import com.nikozka.app.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TaskManagerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:test.properties")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TaskManagerApplication.class)
class TaskIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        taskRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void createValidTaskThanReturn201() throws Exception {
        RequestCreateTaskDto taskDto = new RequestCreateTaskDto("Task description", LocalDate.now());

        saveUser();

        mockMvc.perform(post("/tasks")
                        .with(httpBasic("username", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "USER")
    void createInvalidTaskThanReturn400() throws Exception {
        RequestCreateTaskDto taskDto = new RequestCreateTaskDto("wron", LocalDate.of(20, 01, 12));
        saveUser();
        mockMvc.perform(post("/tasks")
                        .with(httpBasic("username", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage")
                        .value("Description must be between 5 and 255 characters"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getUsersTasksThenReturn200() throws Exception {
        saveUser();
        mockMvc.perform(get("/tasks/current")
                        .with(httpBasic("username", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateTaskStatusThenReturns200() throws Exception {
        StatusDto newStatus = new StatusDto("WORK_IN_PROGRESS");
        mockMvc.perform(put("/tasks/{taskId}/status", saveTask())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStatus)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateTaskStatusInvalidStatusTransactionThenReturns400() throws Exception {
        StatusDto newStatus = new StatusDto("NOTIFIED");
        Long taskId = saveTask();
        mockMvc.perform(put("/tasks/{taskId}/status", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStatus)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Invalid state transition for task with id: " + taskId));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateTaskStatusInvalidStatusTransactionThenReturns404() throws Exception {
        StatusDto newStatus = new StatusDto("NOTIFIED");
        mockMvc.perform(put("/tasks/{taskId}/status", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStatus)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Task not found with id: " + 1));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateTaskStatusInvalidStatusThenReturns400() throws Exception {
        StatusDto newStatus = new StatusDto("Invalid");
        mockMvc.perform(put("/tasks/{taskId}/status", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStatus)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage")
                        .value("Invalid status. Allowed values are PLANNED, WORK_IN_PROGRESS, POSTPONED, NOTIFIED, SIGNED, DONE, CANCELLED"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getTaskByIdThenReturn200() throws Exception {
        saveTask();
        mockMvc.perform(get("/tasks/{taskId}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getTaskByIdThenReturn404() throws Exception {
        mockMvc.perform(get("/tasks/{taskId}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllTasksThenReturns200() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteNotExistedTaskThenReturn404() throws Exception {
        mockMvc.perform(delete("/tasks/{taskId}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteExistedTaskThenReturn204() throws Exception {
        mockMvc.perform(delete("/tasks/{taskId}", saveTask()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteExistedTaskThenReturn403() throws Exception {
        saveTask();
        mockMvc.perform(delete("/tasks/{taskId}", 1L))
                .andExpect(status().isForbidden());
    }

    private Long saveTask() {
        return taskRepository.save(new TaskEntity(1L, "description", LocalDate.now(), TaskStatus.PLANNED, 1L)).getId();
    }

    private void saveUser() {
        userRepository.saveAndFlush(new UserEntity("username", "password"));
    }
}
