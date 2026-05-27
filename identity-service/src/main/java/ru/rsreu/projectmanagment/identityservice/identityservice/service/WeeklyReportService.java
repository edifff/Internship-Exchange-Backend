package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Application;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.EmployerProfile;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.ApplicationRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.EmployerProfileRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeeklyReportService {
    private final ApplicationRepository applicationRepository;
    private final EmployerProfileRepository employerProfileRepository;
    private final EmailService emailService;

    @Transactional(readOnly = true)
    public void generateAndSendWeeklyReports() {
        LocalDate weekAgo = LocalDate.now().minusDays(7);
        List<EmployerProfile> employers = employerProfileRepository.findAll();
        log.info("Starting weekly report generation for {} employers", employers.size());

        for (EmployerProfile employer : employers) {
            try {
                if (employer.getUser() == null) continue;

                List<Application> applications = applicationRepository.findRecentByEmployerId(
                        employer.getUserId(), weekAgo
                );

                // Группировка откликов по названиям вакансий
                Map<String, Long> vacancyStats = applications.stream()
                        .collect(Collectors.groupingBy(
                                app -> app.getVacancy().getTitle() != null ? app.getVacancy().getTitle() : "Без названия",
                                Collectors.counting()
                        ));

                if (!vacancyStats.isEmpty()) {
                    String email = employer.getUser().getEmail();
                    String companyName = employer.getCompanyName() != null ? employer.getCompanyName() : email;
                    emailService.sendWeeklyReport(email, companyName, vacancyStats);
                    log.info("Weekly report sent to employer: {}", email);
                }
            } catch (Exception e) {
                log.error("Failed to send weekly report to employer: {}", employer.getUserId(), e);
            }
        }
        log.info("Weekly report generation completed.");
    }
}
