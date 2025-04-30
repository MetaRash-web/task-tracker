package com.metarash.tasktrackerscheduler.service;

import com.metarash.dto.ReportDto;
import com.metarash.dto.TaskDto;
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
        List<TaskDto> unfinishedTasks = taskService.getUnfinishedTasksByUser(user).stream()
                .map(taskMapper::toDto)
                .toList();
        List<TaskDto> allTasks = taskService.getAllByUser(user).stream()
                .map(taskMapper::toDto)
                .toList();
        List<TaskDto> finishedTasks = taskService.getFinishedTasksByUser(user).stream()
                .map(taskMapper::toDto)
                .toList();

        ReportDto dayReport = new ReportDto();
        dayReport.setEmail(user.getEmail());

        if (allTasks.isEmpty()) {
            return null;
        }

        if (finishedTasks.size() == allTasks.size()) {
            buildFinishedTasks(dayReport, finishedTasks);
        } else if (unfinishedTasks.size() == allTasks.size()) {
            buildUnfinishedTasks(dayReport, unfinishedTasks);
        } else {
            buildAllTaskTasks(dayReport, finishedTasks, unfinishedTasks);
        }

        return dayReport;
    }

    private void buildAllTaskTasks(ReportDto dayReport, List<TaskDto> finishedTasks, List<TaskDto> unfinishedTasks) {
        dayReport.setFinishedCount(finishedTasks.size());
        dayReport.setUnfinishedCount(unfinishedTasks.size());

        dayReport.setFinishedTasks(finishedTasks.size() > 5 ? finishedTasks.subList(0, 5) : finishedTasks);
        dayReport.setUnfinishedTasks(unfinishedTasks.size() > 5 ? unfinishedTasks.subList(0, 5) : unfinishedTasks);
    }

    private void buildUnfinishedTasks(ReportDto dayReport, List<TaskDto> unfinishedTasks) {
        dayReport.setUnfinishedCount(unfinishedTasks.size());
        dayReport.setUnfinishedTasks(unfinishedTasks.size() > 5 ? unfinishedTasks.subList(0, 5) : unfinishedTasks);
    }

    private void buildFinishedTasks(ReportDto dayReport, List<TaskDto> finishedTasks) {
        dayReport.setFinishedCount(finishedTasks.size());
        dayReport.setFinishedTasks(finishedTasks.size() > 5 ? finishedTasks.subList(0, 5) : finishedTasks);
    }
}