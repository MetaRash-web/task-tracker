package com.metarash.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metarash.backend.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
    private TaskStatus status;
    private String username;
}
