package com.metarash.tasktrackerscheduler.service;

import com.metarash.dto.ReportDto;
import com.metarash.dto.TaskDto;
import com.metarash.model.TaskStatus;
import com.metarash.tasktrackerscheduler.entity.Task;
import com.metarash.tasktrackerscheduler.entity.User;
import com.metarash.tasktrackerscheduler.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public ReportDto generateDayReport(User user) {
        List<Task> allTasks = taskService.getAllByUser(user);
        if (allTasks.isEmpty()) {
            return null;
        }

        List<TaskDto> allTaskDtos = allTasks.stream()
                .map(taskMapper::toDto)
                .toList();

        List<TaskDto> finishedTasks = taskService.getFinishedTasksByUser(user).stream()
                .map(taskMapper::toDto)
                .toList();

        List<TaskDto> unfinishedTasks = taskService.getUnfinishedTasksByUser(user).stream()
                .map(taskMapper::toDto)
                .toList();

        ReportDto dayReport = new ReportDto();
        dayReport.setEmail(user.getEmail());

        buildReport(dayReport, finishedTasks, unfinishedTasks, allTaskDtos.size());

        return dayReport;
    }

    private void buildReport(ReportDto report,
                             List<TaskDto> finishedTasks,
                             List<TaskDto> unfinishedTasks,
                             int totalTasks) {
        report.setFinishedCount(finishedTasks.size());
        report.setUnfinishedCount(unfinishedTasks.size());

        if (!finishedTasks.isEmpty()) {
            report.setFinishedTasks(getFirstFive(finishedTasks));
        }
        if (!unfinishedTasks.isEmpty()) {
            report.setUnfinishedTasks(getFirstFive(unfinishedTasks));
        }
    }

    private List<TaskDto> getFirstFive(List<TaskDto> tasks) {
        return tasks.size() > 5 ? tasks.subList(0, 5) : tasks;
    }
}