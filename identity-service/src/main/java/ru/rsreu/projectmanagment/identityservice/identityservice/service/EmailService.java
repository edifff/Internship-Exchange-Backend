package ru.rsreu.projectmanagment.identityservice.identityservice.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Async
    public void sendModerationResult(String to, String vacancyTitle, String decision, String comment) {
        log.info("Sending moderation email to {} | Vacancy: {}, Decision: {}", to, vacancyTitle, decision);

        Context context = new Context();
        context.setVariable("vacancyTitle", vacancyTitle);
        context.setVariable("decision", decision);
        context.setVariable("comment", comment);
        context.setVariable("frontendUrl", frontendUrl);

        String subject = switch (decision.toLowerCase()) {
            case "accepted" -> "✅ Ваша вакансия одобрена";
            case "rejected" -> "❌ Ваша вакансия отклонена";
            case "reviewed" -> "🔄 Ваша вакансия на рассмотрении";
            default -> "📋 Статус вакансии обновлён";
        };

        String htmlContent = templateEngine.process("email/moderation-result", context);
        sendHtmlEmail(to, subject, htmlContent);
    }

    @Async
    public void sendWeeklyReport(String to, String employerName, Map<String, Long> vacancyStats) {
        log.info("Sending weekly report to {} | Vacancies: {}", to, vacancyStats.size());

        Context context = new Context();
        context.setVariable("employerName", employerName != null ? employerName : "Уважаемый работодатель");
        context.setVariable("vacancyStats", vacancyStats);
        context.setVariable("frontendUrl", frontendUrl);
        context.setVariable("reportDate", java.time.LocalDate.now());

        String htmlContent = templateEngine.process("email/weekly-report", context);
        sendHtmlEmail(to, "📊 Еженедельный отчёт по откликам", htmlContent);
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("Email sent to {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", to, e);
        }
    }
}
