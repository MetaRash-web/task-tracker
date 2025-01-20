package com.metarash.backend.service.impl;

import com.metarash.backend.dto.TaskDto;
import com.metarash.backend.entity.Task;
import com.metarash.backend.entity.User;
import com.metarash.backend.mapper.TaskMapper;
import com.metarash.backend.repository.TaskRepository;
import com.metarash.backend.repository.UserRepository;
import com.metarash.backend.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskDto> getTasksByUsername(String username) {
        List<Task> tasks = taskRepository.findTasksByUsername(username);
        System.out.println("tasks: " + tasks);

        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveTask(TaskDto task) {
        taskRepository.save(taskMapper.toEntity(task));
    }
}
