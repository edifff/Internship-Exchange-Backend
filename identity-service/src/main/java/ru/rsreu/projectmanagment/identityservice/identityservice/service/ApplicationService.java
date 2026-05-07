package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateApplicationRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.ApplicationDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.*;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.ApplicationStatus;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.Status;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.ApplicationRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.ProfileStudentRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.ResumeRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.VacancyRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.ApplicationMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

        if (applicationRepository.existsByStudentUserIdAndVacancyId( studentId, request.getVacancyId())) {
            throw new RuntimeException("Application already exists");
        }

        Vacancy vacancy = vacancyRepository.findById(
                request.getVacancyId()
        ).orElseThrow(() -> new RuntimeException("Vacancy not found"));

        if (vacancy.getDeletedAt() != null) { throw new RuntimeException("Vacancy archived");
        }

        if (vacancy.getStatus() != Status.ACCEPTED) {
            throw new RuntimeException( "Vacancy unavailable");
        }

        Resume resume = resumeRepository.findById(
                request.getResumeId()
        ).orElseThrow(() -> new RuntimeException("Resume not found"));

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
    }

    public List<ApplicationDTO> getMyApplications(Authentication auth) {
        UUID studentId =
                ((UserPrincipal) auth.getPrincipal()).getId();

        List<Application> applications=applicationRepository.findAllByStudentUserId(studentId);

        return applicationMapper.toDTO(applications);
    }

    public List<ApplicationDTO> getEmployerApplications(Authentication auth) {
        UUID employerId =
                ((UserPrincipal) auth.getPrincipal()).getId();

        List<Application> applications=applicationRepository.findAllByVacancyEmployerUserId(employerId);

        return applicationMapper.toDTO(applications);
    }
}

