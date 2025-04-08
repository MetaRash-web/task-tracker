package com.metarash.backend.controller;

import com.metarash.backend.dto.TaskDto;
import com.metarash.backend.dto.TaskFilter;
import com.metarash.backend.dto.TaskResponse;
import com.metarash.backend.service.TaskService;
import com.metarash.backend.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<TaskResponse> getTasks(Authentication authentication,
                                                 @ModelAttribute TaskFilter filter) {
        String username = authentication.getName();
        Slice<TaskDto> tasks = taskService.getTasksByUsername(username, filter);
        return ResponseEntity.ok(TaskResponse.fromSlice(tasks));
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto, Authentication authentication) {
        String username = authentication.getName();
        taskDto.setUsername(username);
        TaskDto createdTask = taskService.saveTask(taskDto);
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
        TaskDto updatedTask = taskService.updateTask(taskDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtils.successMessage("Task deleted successfully"));
    }
}
