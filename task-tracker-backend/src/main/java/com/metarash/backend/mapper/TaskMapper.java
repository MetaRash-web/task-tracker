package com.metarash.backend.mapper;

import com.metarash.backend.dto.TaskDto;
import com.metarash.backend.entity.Task;

public interface TaskMapper {

    TaskDto toDto(Task task);

    Task toEntity(TaskDto taskDto);
}
