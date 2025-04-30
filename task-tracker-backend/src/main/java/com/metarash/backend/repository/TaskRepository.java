package com.metarash.backend.repository;

import com.metarash.backend.model.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    @Query("SELECT t FROM Task t JOIN t.user u WHERE u.username = :username")
    Slice<Task> findByUsername(@Param("username") String username, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Task t WHERE t.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);
}