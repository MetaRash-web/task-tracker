package com.metarash.backend.service;

import com.metarash.backend.dto.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> getTasksByUsername(String username);
    void saveTask(TaskDto task);
}
