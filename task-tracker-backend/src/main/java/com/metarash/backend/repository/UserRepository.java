package com.metarash.backend.repository;

import com.metarash.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByIdAndUsername(Long id, String username);
    boolean existsByUsername(String username);
}
