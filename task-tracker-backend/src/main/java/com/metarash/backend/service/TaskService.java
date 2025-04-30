package com.metarash.backend.service;

import com.metarash.dto.TaskDto;
import com.metarash.backend.model.dto.TaskFilter;
import org.springframework.data.domain.Slice;

public interface TaskService {
    Slice<TaskDto>  getTasksByUsername(String username, TaskFilter filter);
    TaskDto saveTask(TaskDto task);
    TaskDto updateTask(TaskDto task);
    void deleteTask(Long id);
}
