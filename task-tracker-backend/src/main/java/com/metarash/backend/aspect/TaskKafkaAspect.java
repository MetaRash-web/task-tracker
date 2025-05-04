package com.metarash.backend.aspect;

import com.metarash.dto.TaskDto;
import com.metarash.dto.TaskEvent;
import com.metarash.dto.TaskEventType;
import com.metarash.backend.model.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TaskKafkaAspect {

    private final KafkaTemplate<String, Object> producer;

    @AfterReturning(
            pointcut = "execution(* com.metarash.backend.service.TaskService.saveTask(..))",
            returning = "result"
    )
    public void afterTaskCreated(TaskDto result) {
        log.info("Task created: id={}, dueDate={}", result.getId(), result.getDueDate());
        producer.send("task-events", new TaskEvent(TaskEventType.CREATED, result.getId(), result.getDueDate()));
        log.info("Sent task created event to Kafka: id={}, dueDate={}", result.getId(), result.getDueDate());
    }

    @AfterReturning(
            pointcut = "execution(* com.metarash.backend.service.impl.TaskServiceImpl.patchTask(..))",
            returning = "result"
    )
    public void afterTaskUpdated(TaskDto result) {
        log.info("Task updated: id={}, dueDate={}", result.getId(), result.getDueDate());
        producer.send("task-events", new TaskEvent(TaskEventType.UPDATED, result.getId(), result.getDueDate()));
        log.info("Sent task updated event to Kafka: id={}, dueDate={}", result.getId(), result.getDueDate());
    }

    @AfterReturning("execution(* com.metarash.backend.service.TaskService.deleteTask(..)) && args(taskId)")
    public void afterTaskDeleted(Long taskId) {
        log.info("Task deleted: id={}", taskId);
        producer.send("task-events", new TaskEvent(TaskEventType.DELETED, taskId, null));
        log.info("Sent task deleted event to Kafka: id={}", taskId);
    }
}
