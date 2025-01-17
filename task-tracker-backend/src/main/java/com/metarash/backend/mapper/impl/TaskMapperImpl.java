package com.metarash.backend.mapper.impl;

import com.metarash.backend.dto.TaskDto;
import com.metarash.backend.entity.Task;
import com.metarash.backend.mapper.TaskMapper;
import com.metarash.backend.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {
    UserService userService;
    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(task.getId(), task.getTitle(), task.getDescription(),
                task.getCreatedAt(), task.getDueDate(), task.getStatus(), task.getUser().getUsername());
    }

    @Override
    public Task toEntity(TaskDto dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCreatedAt(dto.getCreatedAt());
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus());
        task.setUser(userService.findUserByUsername(dto.getUsername()));
        return task;
    }
}
