package com.metarash.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metarash.dto.TaskDto;
import com.metarash.backend.model.dto.TaskFilter;
import com.metarash.backend.model.entity.Task;
import com.metarash.backend.mapper.TaskMapper;
import com.metarash.backend.repository.TaskRepository;
import com.metarash.backend.specification.TaskSpecifications;
import com.metarash.backend.service.TaskService;
import com.metarash.backend.utils.ObjectUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ObjectMapper objectMapper;

    @Override
    public Slice<TaskDto> getTasksByUsername(String username, TaskFilter filter) {
        Sort sort = Sort.unsorted();
        if (filter.getSort() != null) {
            sort = Sort.by(Sort.Order.desc(filter.getSort()));
        }

        log.debug("getTasksByUsername - username: {}, filter: {}", username, filter);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);
        Specification<Task> spec = TaskSpecifications.build(filter, username);

        Slice<TaskDto> tasks = taskRepository.findAll(spec, pageable).map(taskMapper::toDto);
        return tasks;
    }

    @Override
    public TaskDto saveTask(TaskDto taskDto) {
        log.info("Saving task: {}", taskDto);
        Task task = taskMapper.toEntity(taskDto);
        TaskDto createdTask = taskMapper.toDto(taskRepository.save(task));
        log.info("Task saved successfully: {}", createdTask);
        return createdTask;
    }

    @Override
    public TaskDto patchTask(Long id, Map<String, Object> updates, String username) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
        log.info("Updates: {}", updates);
        TaskDto patchDto = objectMapper.convertValue(updates, TaskDto.class);
        Task patchedTask  = ObjectUtils.copyNonNullProperties(task, taskMapper.toEntity(patchDto), "id", "createdAt");

        TaskDto result = taskMapper.toDto(taskRepository.save(patchedTask));
        log.info("Task patched: {}", result);
        return result;
    }

    @Override
    public void deleteTask(Long id) {
        log.info("Attempting to delete task with id: {}", id);
        if (!taskRepository.existsById(id)) {
            log.error("Task not found with id: {}", id);
            throw new EntityNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
        log.info("Task with id: {} deleted successfully", id);
    }
}



