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
import com.nikozka.app.utils.TaskStatusValidatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    TaskRepository taskRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ModelMapper modelMapper;
    @Mock
    TaskStatusValidatorFactory factory;
    @Mock
    MessageSource messageSource;
    @InjectMocks
    TaskService taskService;
    @Captor
    ArgumentCaptor<TaskEntity> argumentCaptor;

    @Test
    void createTaskTestCreateTaskAndReturnId() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("username");

        RequestCreateTaskDto requestCreateTaskDto = new RequestCreateTaskDto("description",
                LocalDate.of(2023, 1,3));
        UserEntity userEntity = new UserEntity("newUser", "password");
        userEntity.setId(1L);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setDescription(requestCreateTaskDto.getDescription());
        taskEntity.setDate(requestCreateTaskDto.getDate());
        taskEntity.setId(1L);

        when(modelMapper.map(requestCreateTaskDto, TaskEntity.class)).thenReturn(taskEntity);
        when(userRepository.findByUsername("username")).thenReturn(userEntity);

        taskEntity.setStatus(TaskStatus.PLANNED);
        taskEntity.setUserId(1L);
        when(taskRepository.save(taskEntity)).thenReturn(taskEntity);

        assertEquals(1L, taskService.createTask(requestCreateTaskDto));
        verify(taskRepository).save(argumentCaptor.capture());

        assertAll("TaskEntity Properties",
                () -> assertEquals(taskEntity.getId(), argumentCaptor.getValue().getId(), "Task ID should match"),
                () -> assertEquals(taskEntity.getStatus(), argumentCaptor.getValue().getStatus(), "Task status should match")
        );
    }

    @Test
    void getTaskByIdTestWhenTaskIsFoundThenReturnTaskDto() {
        TaskEntity taskEntity = new TaskEntity();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(modelMapper.map(taskEntity, TaskDto.class)).thenReturn(new TaskDto());

        TaskDto taskDto = taskService.getTaskById(1L);

        assertNotNull(taskDto);
        verify(taskRepository).findById(1L);
    }

    @Test
    void getTaskByIdTestWhenTaskIsNotFoundThenThrowException() {

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Error");

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1L));

        verify(taskRepository).findById(1L);
    }


    @Test
    void getAllTasksTestShouldReturnListOfTaskDto() {

        Pageable pageable = mock(Pageable.class);
        TaskEntity taskEntity = new TaskEntity();
        Page<TaskEntity> taskPage = new PageImpl<>(List.of(taskEntity));

        when(taskRepository.findAll(pageable)).thenReturn(taskPage);
        when(modelMapper.map(taskEntity, TaskDto.class)).thenReturn(new TaskDto());

        List<TaskDto> taskDtoList = taskService.getAllTasks(pageable);

        assertNotNull(taskDtoList);
        assertEquals(1, taskDtoList.size());
        verify(taskRepository).findAll(pageable);
    }

    @Test
    void deleteTaskTestWhenExistingTaskThenDeleteTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(new TaskEntity()));
        taskService.deleteTask(1L);
        verify(taskRepository).delete(Mockito.any());
    }

    @Test
    void deleteTaskTestWhenNotExistingTaskThenThrowException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Error");

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
        verify(taskRepository, Mockito.times(0)).delete(Mockito.any());
    }

    @Test
    void getTasksForUserTestReturnListOfTaskDto() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("username");

        UserEntity userEntity = new UserEntity("username", "password");
        userEntity.setId(1L);

        Pageable pageable = mock(Pageable.class);

        TaskEntity taskEntity = new TaskEntity();
        Page<TaskEntity> taskPage = new PageImpl<>(List.of(taskEntity));

        when(userRepository.findByUsername("username")).thenReturn(userEntity);
        when(taskRepository.findByUserId(eq(1L), any(Pageable.class))).thenReturn(taskPage);

        when(modelMapper.map(taskEntity, TaskDto.class)).thenReturn(new TaskDto());

        List<TaskDto> taskDtoList = taskService.getTasksForUser(pageable);

        assertNotNull(taskDtoList);
        assertEquals(1, taskDtoList.size());
        verify(taskRepository).findByUserId(eq(1L), any(Pageable.class));
    }


    @Test
    void updateTaskStatusTestWhenValidTransitionShouldUpdateStatus() {

        TaskEntity taskEntity = new TaskEntity();
        StatusDto newStatusDto = new StatusDto("PLANNED");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(factory.getValidator(Mockito.any())).thenReturn((task, status) -> true);
        taskService.updateTaskStatus(1L, newStatusDto);

        verify(taskRepository).save(taskEntity);
    }

    @Test
    void updateTaskStatusTestWhenInvalidTransitionThenThrowException() {

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setStatus(TaskStatus.WORK_IN_PROGRESS);

        StatusDto statusDto = new StatusDto("PLANNED");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Error");
        when(factory.getValidator(taskEntity.getStatus())).thenReturn((task, status) -> false);

        assertThrows(InvalidStateException.class, () -> taskService.updateTaskStatus(1L, statusDto));
    }
}
