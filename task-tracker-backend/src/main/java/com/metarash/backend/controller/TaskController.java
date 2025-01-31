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
        String username = authentication.getName();
        List<TaskDto> tasks = taskService.getTasksByUsername(username);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto, Authentication authentication) {
        String username = authentication.getName();
        taskDto.setUsername(username);
        taskService.saveTask(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "Task created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@RequestBody TaskDto taskDto, Authentication authentication) {
        String username = authentication.getName();
        taskDto.setUsername(username);
        taskService.updateTask(taskDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap("message", "Task updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap("message", "Task deleted successfully"));
    }
}
