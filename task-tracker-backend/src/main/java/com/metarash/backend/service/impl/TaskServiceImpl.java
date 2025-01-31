package com.metarash.backend.service.impl;

import com.metarash.backend.dto.TaskDto;
import com.metarash.backend.entity.Task;
import com.metarash.backend.mapper.TaskMapper;
import com.metarash.backend.repository.TaskRepository;
import com.metarash.backend.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveTask(TaskDto task) {
        taskRepository.save(taskMapper.toEntity(task));
    }

    @Override
    public void updateTask(TaskDto task) {
        Task existingTask = taskRepository.findById(task.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (task.getTitle() != null) {
            existingTask.setTitle(task.getTitle());
        }
        if (task.getDescription() != null) {
            existingTask.setDescription(task.getDescription());
        }
        if (task.getDueDate() != null) {
            existingTask.setDueDate(task.getDueDate());
        }
        if (task.getStatus() != null) {
            existingTask.setStatus(task.getStatus());
        }

        taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
