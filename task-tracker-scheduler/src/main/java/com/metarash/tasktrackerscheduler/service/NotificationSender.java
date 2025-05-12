package com.metarash.tasktrackerscheduler.service;

import com.metarash.dto.NotificationMessage;
import com.metarash.tasktrackerscheduler.entity.Task;
import com.metarash.tasktrackerscheduler.kafka.producer.ReportProducer;
import com.metarash.tasktrackerscheduler.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationSender {
    private final ReportProducer reportProducer;
    private final TaskRepository taskRepository;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public void sendNotification(Long taskId) {
        executor.submit(() -> {
            try {
                Task task = taskRepository.findById(taskId)
                        .orElseThrow(() -> new Exception("task not found with id: " + taskId));

                NotificationMessage message = buildMessage(task);
                reportProducer.sendOverdueTasks(message);
                log.info("Sent notification for task {}", taskId);
            } catch (Exception ex) {
                log.error("Failed to send notification for task {}", taskId, ex);
            }
        });
    }

    private NotificationMessage buildMessage(Task task) {
        return new NotificationMessage(
                task.getId(),
                task.getTitle(),
                String.format("Задача просрочена: '%s'", task.getTitle()),
                task.getUser().getUsername(),
                task.getDueDate()
        );
    }
}
