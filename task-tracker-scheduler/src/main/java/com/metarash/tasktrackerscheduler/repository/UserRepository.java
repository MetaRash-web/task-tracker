package com.metarash.tasktrackerscheduler.repository;


import com.metarash.tasktrackerscheduler.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
