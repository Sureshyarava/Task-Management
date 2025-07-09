package com.example.taskapi.repository;

import com.example.taskapi.dto.TaskResponseDTO;
import com.example.taskapi.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("""
    SELECT t FROM Task t
    WHERE LOWER(t.title) LIKE :pattern
       OR LOWER(t.description) LIKE :pattern
""")
    Page<Task> findTasksContainsTextInDescriptionOrTitle(@Param("pattern") String pattern, Pageable pageable);


    Page<TaskResponseDTO> findTaskByDueDateIsLessThan(LocalDateTime dueDateIsGreaterThan, Pageable pageable);
}
