package com.metarash.backend.service.impl;

import com.metarash.backend.dto.TaskDto;
import com.metarash.backend.dto.TaskFilter;
import com.metarash.backend.entity.Task;
import com.metarash.backend.entity.User;
import com.metarash.backend.mapper.TaskMapper;
import com.metarash.backend.repository.TaskRepository;
import com.metarash.backend.specification.TaskSpecifications;
import com.metarash.backend.service.TaskService;
import com.metarash.backend.service.UserService;
import com.metarash.backend.utils.ObjectUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public Slice<TaskDto> getTasksByUsername(String username, TaskFilter filter) {
        Sort sort = Sort.unsorted();
        if (filter.getSort() != null) {
            sort = Sort.by(Sort.Order.desc(filter.getSort()));
        }

        System.out.println("username: " + username + " filter: " + filter);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);
        Specification<Task> spec = TaskSpecifications.build(filter, username);

        return taskRepository.findAll(spec, pageable).map(taskMapper::toDto);
    }

    @Override
    public TaskDto saveTask(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto) {
        Task existingTask = taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskDto.getId()));

        Task updatedTask = ObjectUtils.copyNonNullProperties(existingTask, taskMapper.toEntity(taskDto), "id", "createdAt");

        return taskMapper.toDto(taskRepository.save(updatedTask));
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}



