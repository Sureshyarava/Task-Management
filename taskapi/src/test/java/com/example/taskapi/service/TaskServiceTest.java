package com.example.taskapi.service;

import com.example.taskapi.dto.TaskRequestDTO;
import com.example.taskapi.dto.TaskResponseDTO;
import com.example.taskapi.entity.Task;
import com.example.taskapi.entity.TaskPriority;
import com.example.taskapi.entity.TaskStatus;
import com.example.taskapi.exception.TaskNotFoundException;
import com.example.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task sampleTask;
    private TaskRequestDTO taskRequestDTO;

    @BeforeEach
    void setUp() {
        sampleTask = new Task();
        sampleTask.setId(1L);
        sampleTask.setTitle("Sample Task");
        sampleTask.setDescription("Do something important");
        sampleTask.setStatus(TaskStatus.TODO);
        sampleTask.setPriority(TaskPriority.HIGH);
        sampleTask.setDueDate(LocalDateTime.now().plusDays(1));
        sampleTask.setCreatedAt(LocalDateTime.now());
        sampleTask.setTags(Set.of("spring"));
        sampleTask.setEstimatedHours(24);

        taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setTitle("Sample Task");
        taskRequestDTO.setDescription("Do something important");
        taskRequestDTO.setDueDate(LocalDateTime.now().plusDays(1));
        taskRequestDTO.setStatus(TaskStatus.TODO);
        taskRequestDTO.setPriority(TaskPriority.HIGH);
        taskRequestDTO.setTags(Set.of("spring"));
    }

    @Test
    void testGetTaskSuccess() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        TaskResponseDTO result = taskService.getTask(1L);

        assertEquals("Sample Task", result.getTitle());
        verify(taskRepository).findById(1L);
    }

    @Test
    void testGetTaskNotFound() {
        when(taskRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(2L));
    }

    @Test
    void testCreateTaskSuccess() {
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        TaskResponseDTO result = taskService.createTask(taskRequestDTO);

        assertEquals("Sample Task", result.getTitle());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testRemoveTask() {
        doNothing().when(taskRepository).deleteById(1L);
        taskService.removeTask(1L);
        verify(taskRepository).deleteById(1L);
    }
}
