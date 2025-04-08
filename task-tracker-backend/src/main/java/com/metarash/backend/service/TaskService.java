package com.metarash.backend.service;

import com.metarash.backend.dto.TaskDto;
import com.metarash.backend.dto.TaskFilter;
import com.metarash.backend.entity.Task;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface TaskService {
    Slice<TaskDto>  getTasksByUsername(String username, TaskFilter filter);
    TaskDto saveTask(TaskDto task);
    TaskDto updateTask(TaskDto task);
    void deleteTask(Long id);
}
