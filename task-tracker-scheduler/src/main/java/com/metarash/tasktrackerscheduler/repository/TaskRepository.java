package com.metarash.tasktrackerscheduler.repository;



import com.metarash.model.TaskStatus;
import com.metarash.tasktrackerscheduler.entity.Task;
import com.metarash.tasktrackerscheduler.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    List<Task> findByUserAndStatus(User user, TaskStatus status);
    List<Task> findByUserAndStatusNot(User user, TaskStatus status);
}