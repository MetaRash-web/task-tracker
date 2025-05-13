package com.metarash.tasktrackerscheduler.service;

import com.metarash.tasktrackerscheduler.entity.Task;
import com.metarash.tasktrackerscheduler.entity.User;
import com.metarash.model.TaskStatus;
import com.metarash.tasktrackerscheduler.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getAllByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public List<Task> getFinishedTasksByUser(User user) {
        return taskRepository.findByUserAndStatus(user, TaskStatus.completed);
    }

    public List<Task> getUnfinishedTasksByUser(User user) {
        return taskRepository.findByUserAndStatusNot(user, TaskStatus.completed);
    }
}
