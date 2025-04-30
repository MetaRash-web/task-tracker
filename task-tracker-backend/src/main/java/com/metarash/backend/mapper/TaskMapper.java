package com.metarash.backend.mapper;

import com.metarash.dto.TaskDto;
import com.metarash.backend.model.entity.Task;

public interface TaskMapper {

    TaskDto toDto(Task task);

    Task toEntity(TaskDto taskDto);
}
