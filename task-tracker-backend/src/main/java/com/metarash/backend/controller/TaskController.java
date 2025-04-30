package com.metarash.backend.controller;

import com.metarash.dto.TaskDto;
import com.metarash.backend.model.dto.TaskFilter;
import com.metarash.backend.model.dto.TaskResponse;
import com.metarash.backend.service.TaskService;
import com.metarash.backend.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<TaskResponse> getTasks(Authentication authentication,
                                                 @ModelAttribute TaskFilter filter) {
        String username = authentication.getName();
        log.info("GET /tasks — user: {}, filter: {}", username, filter);
        Slice<TaskDto> tasks = taskService.getTasksByUsername(username, filter);
        return ResponseEntity.ok(TaskResponse.fromSlice(tasks));
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto, Authentication authentication) {
        String username = authentication.getName();
        taskDto.setUsername(username);
        log.info("POST /tasks — user: {}, task: {}", username, taskDto);
        TaskDto createdTask = taskService.saveTask(taskDto);
        log.debug("Task created: {}", createdTask);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id,
                                        @RequestBody TaskDto taskDto,
                                        Authentication authentication) {
        String username = authentication.getName();
        taskDto.setId(id);
        taskDto.setUsername(username);
        log.info("PUT /tasks/{} — user: {}, newData: {}", id, username, taskDto);
        TaskDto updatedTask = taskService.updateTask(taskDto);
        log.debug("Task updated: {}", updatedTask);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        log.info("DELETE /tasks/{} — request to delete task", id);
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtils.successMessage("Task deleted successfully"));
    }
}

