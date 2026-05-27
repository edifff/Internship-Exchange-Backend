package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateApplicationRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.ApplicationDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.ApplicationStatus;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.Status;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.ApplicationRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.ProfileStudentRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.ResumeRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.VacancyRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.ForbiddenException;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.NotFoundException;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.ApplicationMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final VacancyRepository vacancyRepository;
    private final ResumeRepository resumeRepository;
    private final ProfileStudentRepository studentRepository;
    private final ApplicationMapper applicationMapper;

    public void apply(CreateApplicationRequest request, Authentication auth) {
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        UUID studentId = principal.getId();
        log.info("Application attempt: student={}, vacancy={}", studentId, request.getVacancyId());

        if (applicationRepository.existsByStudentUserIdAndVacancyId(studentId, request.getVacancyId())) {
            throw new EntityExistsException("Application already exists");
        }

        Vacancy vacancy = vacancyRepository.findById(request.getVacancyId())
                .orElseThrow(() -> new NotFoundException("Vacancy not found"));
        if (vacancy.getDeletedAt() != null) {
            throw new ForbiddenException("Vacancy archived");
        }
        if (vacancy.getStatus() != Status.ACCEPTED) {
            throw new ForbiddenException("Vacancy unavailable");
        }

        Resume resume = resumeRepository.findByStudentUserId(studentId)
                .orElseThrow(() -> new NotFoundException("Please upload your resume first"));

        if (request.getResumeId() != null && !resume.getId().equals(request.getResumeId())) {
            log.warn("Client sent resumeId={}, but system uses actual student resumeId={}",
                    request.getResumeId(), resume.getId());
        }

        StudentProfile student = studentRepository.findByUserId(studentId);

        Application application = Application.builder()
                .student(student)
                .vacancy(vacancy)
                .resume(resume)
                .coverLetter(request.getCoverLetter())
                .status(ApplicationStatus.PENDING)
                .createdAt(LocalDate.now())
                .build();

        applicationRepository.save(application);
        log.info("Application created successfully: applicationId={}, student={}, vacancy={}",
                application.getId(), studentId, request.getVacancyId());
    }

    public List<ApplicationDTO> getMyApplications(Authentication auth) {
        UUID studentId =
                ((UserPrincipal) auth.getPrincipal()).getId();

        List<Application> applications=applicationRepository.findAllByStudentUserId(studentId);

        return applicationMapper.toDTO(applications);
    }

    @Transactional(readOnly = true)
    public List<ApplicationDTO> getEmployerApplications(Authentication auth) {
        UUID employerId =
                ((UserPrincipal) auth.getPrincipal()).getId();

        List<Application> applications=applicationRepository.findAllByVacancyEmployerUserId(employerId);

        return applicationMapper.toDTO(applications);
    }
}

