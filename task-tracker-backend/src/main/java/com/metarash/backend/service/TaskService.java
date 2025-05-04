package com.metarash.backend.service;

import com.metarash.dto.TaskDto;
import com.metarash.backend.model.dto.TaskFilter;
import org.springframework.data.domain.Slice;

import java.util.Map;

public interface TaskService {
    Slice<TaskDto>  getTasksByUsername(String username, TaskFilter filter);
    TaskDto saveTask(TaskDto task);
    TaskDto patchTask(Long id, Map<String, Object> updates, String username);
    void deleteTask(Long id);
}
