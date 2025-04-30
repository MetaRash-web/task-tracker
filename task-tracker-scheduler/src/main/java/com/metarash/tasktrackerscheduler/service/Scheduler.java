package com.metarash.tasktrackerscheduler.service;


import com.metarash.dto.ReportDto;
import com.metarash.tasktrackerscheduler.entity.User;
import com.metarash.tasktrackerscheduler.kafka.producer.ReportProducer;
import com.metarash.tasktrackerscheduler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {

    private final ReportProducer reportProducer;
    private final UserRepository userRepository;
    private final ReportService reportService;

    @Scheduled(cron = "0 0 0 * * *")
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
            reportProducer.sendAllTasks(dayReport);
        } else if (dayReport.getUnfinishedCount() > 0) {
            reportProducer.sendUnfinishedTasks(dayReport);
        } else if (dayReport.getFinishedCount() > 0) {
            reportProducer.sendFinishedTasks(dayReport);
        }
    }
}
