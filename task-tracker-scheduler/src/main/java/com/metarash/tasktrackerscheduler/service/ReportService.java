package com.metarash.tasktrackerscheduler.service;

import com.metarash.tasktrackerscheduler.dto.ReportDto;
import com.metarash.tasktrackerscheduler.entity.Task;
import com.metarash.tasktrackerscheduler.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TaskService taskRepository;

    public ReportDto generateDayReport(User user) {
        List<Task> allTasks = taskRepository.getAllByUser(user);
        List<Task> finishedTasks = taskRepository.getFinishedTasksByUser(user);
        List<Task> unfinishedTasks = taskRepository.getUnfinishedTasksByUser(user);

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

    private void buildAllTaskTasks(ReportDto dayReport, List<Task> finishedTasks, List<Task> unfinishedTasks) {
        dayReport.setFinishedCount(finishedTasks.size());
        dayReport.setUnfinishedCount(unfinishedTasks.size());

        dayReport.setFinishedTasks(finishedTasks.size() > 5 ? finishedTasks.subList(0, 5) : finishedTasks);
        dayReport.setUnfinishedTasks(unfinishedTasks.size() > 5 ? unfinishedTasks.subList(0, 5) : unfinishedTasks);
    }

    private void buildUnfinishedTasks(ReportDto dayReport, List<Task> unfinishedTasks) {
        dayReport.setUnfinishedCount(unfinishedTasks.size());
        dayReport.setUnfinishedTasks(unfinishedTasks.size() > 5 ? unfinishedTasks.subList(0, 5) : unfinishedTasks);
    }

    private void buildFinishedTasks(ReportDto dayReport, List<Task> finishedTasks) {
        dayReport.setFinishedCount(finishedTasks.size());
        dayReport.setFinishedTasks(finishedTasks.size() > 5 ? finishedTasks.subList(0, 5) : finishedTasks);
    }
}