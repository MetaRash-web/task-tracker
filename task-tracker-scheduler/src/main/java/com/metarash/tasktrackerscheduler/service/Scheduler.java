package com.metarash.tasktrackerscheduler.service;


import com.metarash.tasktrackerscheduler.dto.ReportDto;
import com.metarash.tasktrackerscheduler.entity.User;
import com.metarash.tasktrackerscheduler.producer.Producer;
import com.metarash.tasktrackerscheduler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {

    private final Producer producer;
    private final UserRepository userRepository;
    private final ReportService reportService;

    @Scheduled(cron = "0 0 0 * * *")  // Раз в день, в полночь
    public void sendDayReport() {
        for (User user : userRepository.findAll()) {
            ReportDto dayReport = reportService.generateDayReport(user);
            if (dayReport != null) {
                sendReport(dayReport);
            }
        }
    }

    private void sendReport(ReportDto dayReport) {
        if (dayReport.getFinishedCount() > 0 && dayReport.getUnfinishedCount() > 0) {
            producer.sendAllTasks(dayReport);
        } else if (dayReport.getUnfinishedCount() > 0) {
            producer.sendUnfinishedTasks(dayReport);
        } else if (dayReport.getFinishedCount() > 0) {
            producer.sendFinishedTasks(dayReport);
        }
    }
}
