package com.example.taskapi.dto;

import com.example.taskapi.entity.TaskPriority;
import com.example.taskapi.entity.TaskStatus;
import lombok.*;


import java.time.LocalDateTime;
import java.util.Set;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {
    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private LocalDateTime dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Set<String> tags;

    private Integer estimatedHours;

    private Integer actualHours;
}

