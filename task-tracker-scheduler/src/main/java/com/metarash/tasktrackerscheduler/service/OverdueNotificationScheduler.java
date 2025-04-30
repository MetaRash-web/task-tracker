package com.metarash.tasktrackerscheduler.service;

import com.metarash.dto.TaskDto;
import com.metarash.model.TaskStatus;
import com.metarash.dto.NotificationMessage;
import com.metarash.tasktrackerscheduler.entity.Task;
import com.metarash.tasktrackerscheduler.kafka.producer.ReportProducer;
import com.metarash.tasktrackerscheduler.mapper.TaskMapper;
import com.metarash.tasktrackerscheduler.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledFuture;


@Slf4j
@Component
@RequiredArgsConstructor
public class OverdueNotificationScheduler {

    private final TaskRepository taskRepository;
    private final ReportProducer reportProducer;
    private final TaskScheduler taskScheduler;
    private final TaskMapper taskMapper;

    private ScheduledFuture<?> future;

    private volatile LocalDateTime nextDueDate;

    @PostConstruct
    public void init() {
        this.nextDueDate = taskRepository.findNextDueDateAfterNow(LocalDateTime.now())
                .orElse(LocalDateTime.MAX);
        scheduleNextCheck();
    }

    private void scheduleNextCheck() {
        if (nextDueDate.equals(LocalDateTime.MAX)) {
            log.info("No next due date set — skipping schedule");
            return;
        }

        Duration delay = Duration.between(LocalDateTime.now(), nextDueDate);
        if (delay.isNegative() || delay.isZero()) delay = Duration.ofSeconds(1); // safety

        future = taskScheduler.schedule(this::checkOverdueTasks, Instant.now().plus(delay));
        log.info("Next overdue check scheduled at {}", nextDueDate);
    }

    private void checkOverdueTasks() {
        LocalDateTime now = LocalDateTime.now();
        List<TaskDto> overdueTasks = taskRepository.findAllByDueDateBeforeAndStatusNotAndNotificationSentIsNull(now, TaskStatus.completed)
                .stream()
                .map(taskMapper::toDto)
                .toList();

        log.info("Overdue tasks found: {}", overdueTasks);

        for (TaskDto task : overdueTasks) {
            NotificationMessage notification = buildNotification(task);
            log.info("Notification: {}", notification);
            reportProducer.sendOverdueTasks(notification);
        }

        this.nextDueDate = taskRepository.findNextDueDateAfterNow(LocalDateTime.now())
                .orElse(LocalDateTime.MAX);
        scheduleNextCheck();
    }

    public void rescheduleNextCheck() {
        log.info("Rescheduling next overdue check...");
        if (future != null) {
            boolean cancelled = future.cancel(false);
            log.info("Previous scheduled task cancelled: {}", cancelled);
        } else {
            log.info("No previous scheduled task to cancel.");
        }
        scheduleNextCheck();
    }

    public void updateDueDateIfEarlier(LocalDateTime incomingDueDate) {
        if (incomingDueDate.isBefore(this.nextDueDate)) {
            log.info("New earlier dueDate from Kafka: {} < {}, rescheduling...", incomingDueDate, this.nextDueDate);
            this.nextDueDate = incomingDueDate;
            rescheduleNextCheck();
        }
    }


    private NotificationMessage buildNotification(TaskDto task) {
        return new NotificationMessage(
                task.getId(),
                task.getTitle(),
                "Задача просрочена: \"" + task.getTitle() + "\". Срок: " + task.getDueDate(),
                task.getUsername(),
                task.getDueDate()
        );
    }
}


