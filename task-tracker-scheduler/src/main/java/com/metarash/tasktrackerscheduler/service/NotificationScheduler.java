package com.metarash.tasktrackerscheduler.service;

import com.metarash.dto.TaskEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationScheduler {
    private final NotificationSender notificationSender;


    private final PriorityQueue<ScheduledTask> queue =
            new PriorityQueue<>(Comparator.comparing(ScheduledTask::dueDate));

    private final ReentrantLock lock = new ReentrantLock();

    private final Timer timer = new Timer();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> currentTask;

    public void scheduleNotification(TaskEvent event) {
        lock.lock();
        try {
            queue.add(new ScheduledTask(event.getTaskId(), event.getDueDate()));
            ScheduledTask peek = queue.peek();
            if (peek == null) {
                log.info("No tasks in queue - nothing to schedule");
                return;
            }
            if (peek.dueDate().equals(event.getDueDate())) {
                reschedule();
            }
        } finally {
            lock.unlock();
        }
    }

    private void reschedule() {
        lock.lock();
        try {
            if (currentTask != null) {
                currentTask.cancel(false);
            }

            ScheduledTask nextTask = queue.peek();
            if (nextTask == null) {
                log.info("No tasks in queue - nothing to schedule");
                return;
            }

            long delay = Duration.between(LocalDateTime.now(), nextTask.dueDate())
                    .toMillis();

            if (delay < 0) {
                log.warn("Task is already overdue: {}", nextTask.taskId());
                delay = 0;
            }

            currentTask = scheduler.schedule(
                this::handleDueTask,
                delay,
                TimeUnit.MILLISECONDS);

            log.info("Scheduled task {} for execution in {} ms", nextTask.taskId(), delay);
        } finally {
            lock.unlock();
        }
    }

    private void handleDueTask() {
        lock.lock();
        try {
            ScheduledTask task = queue.poll();
            if (task == null) {
                log.warn("No task found in queue - possible race condition");
                return;
            }
            notificationSender.sendNotification(task.taskId());

            if (!queue.isEmpty()) {
                reschedule();
            }
        } finally {
            lock.unlock();
        }
    }

    private record ScheduledTask(Long taskId, LocalDateTime dueDate) {}
}
