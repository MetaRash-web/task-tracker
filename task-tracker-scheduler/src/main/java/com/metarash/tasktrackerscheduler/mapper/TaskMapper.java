package com.metarash.tasktrackerscheduler.mapper;

import com.metarash.dto.TaskDto;
import com.metarash.model.TaskPriority;
import com.metarash.model.TaskStatus;
import com.metarash.tasktrackerscheduler.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDto toDto(Task task) {
        if (task == null) return null;

        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(TaskStatus.valueOf(task.getStatus().name()))
                .createdAt(task.getCreatedAt())
                .dueDate(task.getDueDate())
                .completedAt(task.getCompletedAt())
                .username(task.getUser().getUsername())
                .priority(TaskPriority.valueOf(task.getPriority().name()))
                .build();
    }
}
