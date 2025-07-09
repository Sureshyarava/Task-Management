package com.example.taskapi.controller;

import com.example.taskapi.dto.TaskResponseDTO;
import com.example.taskapi.entity.TaskPriority;
import com.example.taskapi.entity.TaskStatus;
import com.example.taskapi.exception.TaskNotFoundException;
import com.example.taskapi.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    private Page<TaskResponseDTO> mockTaskPage;

    @BeforeEach
    void setUp() {
        TaskResponseDTO task1 = new TaskResponseDTO(
                1L, "My first Task", "I need to complete the project", TaskStatus.TODO, TaskPriority.HIGH,
                LocalDateTime.of(2025, 7, 10, 0, 0),
                LocalDateTime.of(2025, 7, 9, 9, 48, 58, 804107000),
                null, Set.of("spring", "imp"), 14, null
        );

        TaskResponseDTO task2 = new TaskResponseDTO(
                2L, "Fix bugs in Task module", "Resolve known issues before next sprint", TaskStatus.IN_PROGRESS, TaskPriority.MEDIUM,
                LocalDateTime.of(2025, 7, 15, 0, 0),
                LocalDateTime.of(2025, 7, 9, 10, 30),
                LocalDateTime.of(2025, 7, 9, 15, 0),
                Set.of("bugfix", "backend"), 8, 3
        );

        List<TaskResponseDTO> taskList = List.of(task1, task2);
        mockTaskPage = new PageImpl<>(taskList);
    }

    @Test
    public void getAllTasks() throws Exception {
        when(taskService.getTasks(0,10)).thenReturn(mockTaskPage);
        mockMvc.perform(get("/api/v1/tasks/")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    public void getTaskById() throws Exception {
        when(taskService.getTask(1L)).thenReturn(mockTaskPage.getContent().get(0));
        mockMvc.perform(get("/api/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void getTaskByIdNotFound() throws Exception {
        when(taskService.getTask(3L)).thenThrow(new TaskNotFoundException("Task not found"));
        mockMvc.perform(get("/api/v1/tasks/3"))
                .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Task not found"));
        verify(taskService).getTask(3L);
    }
}
