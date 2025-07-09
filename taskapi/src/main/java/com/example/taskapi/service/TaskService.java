package com.example.taskapi.service;

import com.example.taskapi.dto.TaskMapper;
import com.example.taskapi.dto.TaskRequestDTO;
import com.example.taskapi.dto.TaskResponseDTO;
import com.example.taskapi.dto.TaskUpdateDTO;
import com.example.taskapi.entity.Task;
import com.example.taskapi.entity.TaskStatus;
import com.example.taskapi.exception.TaskNotFoundException;
import com.example.taskapi.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Page<TaskResponseDTO> getTasks(Integer page, Integer size){
        return taskRepository.findAll(PageRequest.of(page,size)).map(TaskMapper::toTaskResponseDTO);
    }

    public TaskResponseDTO getTask(Long id){
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not Found"));
        return TaskMapper.toTaskResponseDTO(task);
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO){
       Task task = TaskMapper.toTask(taskRequestDTO);
       LocalDateTime now = LocalDateTime.now();
       task.setCreatedAt(now);
       task.setEstimatedHours(getEstimatedHours(task.getDueDate(),now));
       taskRepository.save(task);
       return TaskMapper.toTaskResponseDTO(task);
    }

    public TaskResponseDTO updateTask(@PathVariable Long id, TaskUpdateDTO taskUpdateDTO){
        taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not Found"));
        Task task = TaskMapper.toTask(taskUpdateDTO);
        LocalDateTime presentTime = LocalDateTime.now();
        task.setUpdatedAt(presentTime);
        task.setEstimatedHours(getEstimatedHours(task.getDueDate(),presentTime));
        return TaskMapper.toTaskResponseDTO(taskRepository.save(task));
    }

    public TaskResponseDTO partialUpdateTask(@PathVariable Long id, @RequestBody TaskUpdateDTO taskUpdateDTO){
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not Found"));
        LocalDateTime presentTime = LocalDateTime.now();
        task.setUpdatedAt(presentTime);
        task.setEstimatedHours(getEstimatedHours(task.getDueDate(),presentTime));
        TaskMapper.mapNonNullFields(task, taskUpdateDTO);
        return TaskMapper.toTaskResponseDTO(taskRepository.save(task));
    }

    public void removeTask(@PathVariable Long id){
        taskRepository.deleteById(id);
    }

    public Page<TaskResponseDTO> getTasksContainsTextInDescriptionOrTitle(String text, Integer page, Integer size){
        String pattern = "%" + text.toLowerCase()+ "%";
        return taskRepository.findTasksContainsTextInDescriptionOrTitle(pattern, PageRequest.of(page, size)).map(TaskMapper::toTaskResponseDTO);
    }

    public TaskResponseDTO updateTaskStatus(Long id, TaskStatus taskStatus){
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not Found"));
        task.setStatus(taskStatus);
        if( taskStatus == TaskStatus.DONE){
            task.setActualHours((int) Duration.between(task.getCreatedAt(), LocalDateTime.now()).toHours());
        } else {
            task.setActualHours(null);
        }
        task.setUpdatedAt(LocalDateTime.now());
        return TaskMapper.toTaskResponseDTO(taskRepository.save(task));
    }

    public Page<TaskResponseDTO> getOverDueTasks(Integer page, Integer size){
        return taskRepository.findTaskByDueDateIsLessThan(LocalDateTime.now(), PageRequest.of(page,size));
    }

    private static Integer getEstimatedHours(LocalDateTime dueDate, LocalDateTime now){
        return (int) Duration.between(now, dueDate).toHours();
    }
}