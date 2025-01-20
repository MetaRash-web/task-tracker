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
        return new TaskDto(task.getId(), task.getTitle(), task.getDescription(),
                task.getCreatedAt(), task.getDueDate(), task.getStatus(), task.getUser().getUsername());
    }

    @Override
    public Task toEntity(TaskDto dto) {
        // Создаём новый объект Task
        Task task = new Task();

        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus());

        User user = userService.findUserByUsername(dto.getUsername());

        if (user == null) {
            throw new IllegalArgumentException("User not found with username: " + dto.getUsername());
        }

        task.setUser(user);

        return task;
    }
}
