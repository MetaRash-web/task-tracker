package com.metarash.backend.controller;

import com.metarash.backend.dto.TaskDto;
import com.metarash.backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:80")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<?> getTasks(Authentication authentication) {
        System.out.println("get tasks method started");
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("some error with authentication");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Unauthorized"));
        }
        System.out.println("auth: " + authentication);

        String username = authentication.getName();
        try {
            List<TaskDto> tasks = taskService.getTasksByUsername(username);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Failed to retrieve tasks"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Unauthorized"));
        }

        String username = authentication.getName();
        taskDto.setUsername(username);

        try {
            taskService.saveTask(taskDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("message", "Task created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Failed to create task"));
        }
    }
}
