package com.metarash.tasktrackerscheduler.repository;



import com.metarash.tasktrackerscheduler.entity.Task;
import com.metarash.tasktrackerscheduler.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t JOIN t.user u WHERE u.username = :username")
    List<Task> findTasksByUsername(@Param("username") String username);

    List<Task> findByUser(User user);
}