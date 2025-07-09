package com.example.taskapi.dto;

import com.example.taskapi.entity.Task;

public class TaskMapper {

    public static TaskResponseDTO toTaskResponseDTO(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .tags(task.getTags())
                .estimatedHours(task.getEstimatedHours())
                .actualHours(task.getActualHours())
                .build();
    }

    public static Task toTask(TaskRequestDTO taskRequestDTO) {
        return Task.builder()
                .title(taskRequestDTO.getTitle())
                .description(taskRequestDTO.getDescription())
                .status(taskRequestDTO.getStatus())
                .priority(taskRequestDTO.getPriority())
                .dueDate(taskRequestDTO.getDueDate())
                .tags(taskRequestDTO.getTags())
                .build();
    }
    public static Task toTask(TaskUpdateDTO taskUpdateDTO) {
        return  Task.builder()
                .id(taskUpdateDTO.getId())
                .title(taskUpdateDTO.getTitle())
                .description(taskUpdateDTO.getDescription())
                .status(taskUpdateDTO.getStatus())
                .priority(taskUpdateDTO.getPriority())
                .dueDate(taskUpdateDTO.getDueDate())
                .createdAt(taskUpdateDTO.getCreatedAt())
                .tags(taskUpdateDTO.getTags())
                .actualHours(taskUpdateDTO.getActualHours())
                .build();
    }

    public static void mapNonNullFields(Task task, TaskUpdateDTO taskUpdateDTO) {
        if (taskUpdateDTO.getTitle() != null) task.setTitle(taskUpdateDTO.getTitle());
        if (taskUpdateDTO.getDescription() != null) task.setDescription(taskUpdateDTO.getDescription());
        if (taskUpdateDTO.getStatus() != null) task.setStatus(taskUpdateDTO.getStatus());
        if (taskUpdateDTO.getPriority() != null) task.setPriority(taskUpdateDTO.getPriority());
        if (taskUpdateDTO.getDueDate() != null) task.setDueDate(taskUpdateDTO.getDueDate());
        if (taskUpdateDTO.getTags() != null) task.setTags(taskUpdateDTO.getTags());
        if (taskUpdateDTO.getActualHours()!=null) task.setActualHours(taskUpdateDTO.getActualHours());
    }

}
