package ru.rsreu.projectmanagment.identityservice.identityservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.rsreu.projectmanagment.identityservice.identityservice.service.WeeklyReportService;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeeklyReportScheduler {
    private final WeeklyReportService weeklyReportService;

    // Запуск каждый понедельник в 10:00. Можно вынести в application.yml через ${cron.weekly.report}
    @Scheduled(cron = "0 0 10 ? * MON")
    public void scheduleWeeklyReports() {
        log.info("Cron triggered: starting scheduled weekly reports...");
        weeklyReportService.generateAndSendWeeklyReports();
    }
}