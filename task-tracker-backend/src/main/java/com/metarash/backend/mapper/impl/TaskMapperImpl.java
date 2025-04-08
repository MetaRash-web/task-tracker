package com.metarash.backend.mapper.impl;

import com.metarash.backend.dto.TaskDto;
import com.metarash.backend.entity.Task;
import com.metarash.backend.entity.User;
import com.metarash.backend.mapper.TaskMapper;
import com.metarash.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapperImpl implements TaskMapper {
    private final UserService userService;

    @Override
    public TaskDto toDto(Task task) {
        if (task == null) {
            return null;
        }

        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .createdAt(task.getCreatedAt())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .username(task.getUser() != null ? task.getUser().getUsername() : null)
                .priority(task.getPriority())
                .build();
    }

    @Override
    public Task toEntity(TaskDto taskDto) {
        if (taskDto == null) {
            return null;
        }

        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setStatus(taskDto.getStatus());
        task.setPriority(taskDto.getPriority());

        if (taskDto.getUsername() != null) {
            User user = userService.findUserByUsername(taskDto.getUsername());
            if (user == null) {
                throw new IllegalArgumentException("User not found with username: " + taskDto.getUsername());
            }
            task.setUser(user);
        }

        return task;
    }
}
