package com.metarash.backend.repository;

import com.metarash.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t JOIN t.user u WHERE u.username = :username")
    List<Task> findTasksByUsername(@Param("username") String username);
}
