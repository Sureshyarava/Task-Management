package com.example.taskapi.controller;

import com.example.taskapi.dto.TaskRequestDTO;
import com.example.taskapi.dto.TaskResponseDTO;
import com.example.taskapi.dto.TaskUpdateDTO;
import com.example.taskapi.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Page<TaskResponseDTO> getTasks(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return  taskService.getTasks(page, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDTO getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDTO createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        return taskService.createTask(taskRequestDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDTO updateTask(@PathVariable Long id, @RequestBody TaskUpdateDTO taskUpdateDTO) {
        return taskService.updateTask(id, taskUpdateDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDTO patchTask(@PathVariable Long id, @RequestBody TaskUpdateDTO taskUpdateDTO) {
        return taskService.partialUpdateTask(id, taskUpdateDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.removeTask(id);
    }

    @GetMapping("/search")
    public Page<TaskResponseDTO> getTasksContainsTextInDescriptionOrTitle(@RequestParam(defaultValue = "") String text, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        return taskService.getTasksContainsTextInDescriptionOrTitle(text, page, size);
    }

    @PatchMapping("/{id}/status")
    public TaskResponseDTO updateStatus(@PathVariable Long id, @RequestBody TaskUpdateDTO taskUpdateDTO) {
        return taskService.updateTaskStatus(id, taskUpdateDTO.getStatus());
    }

    @GetMapping("/overdue")
    public Page<TaskResponseDTO> getOverDueTasks(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return taskService.getOverDueTasks(page, size);
    }
}