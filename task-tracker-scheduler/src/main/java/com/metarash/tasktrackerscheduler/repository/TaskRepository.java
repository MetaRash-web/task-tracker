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
    @Query("SELECT MIN(t.dueDate) FROM Task t WHERE t.dueDate > :now AND t.status != 'completed' AND t.notificationSent IS NULL")
    Optional<LocalDateTime> findNextDueDateAfterNow(@Param("now") LocalDateTime now);

    List<Task> findAllByDueDateBeforeAndStatusNotAndNotificationSentIsNull(LocalDateTime dueDate, TaskStatus status);
}