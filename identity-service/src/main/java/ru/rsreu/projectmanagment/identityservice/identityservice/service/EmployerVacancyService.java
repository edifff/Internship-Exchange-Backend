package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.EmployerProfile;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Specialty;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.UserPrincipal;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Vacancy;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.Status;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.EmployerProfileRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.VacancyRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.exception.NotFoundException;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.VacancyMapper;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class EmployerVacancyService {

    private final VacancyRepository vacancyRepository;
    private final EmployerProfileRepository employerProfileRepository;
    private final VacancyMapper vacancyMapper;
    private final SpecialtiesService specialtiesService;

    @Transactional
    public void create(CreateVacancyRequest request) {
        EmployerProfile employer = getCurrentEmployer();
        log.info("Creating vacancy by employer: {}", employer.getUserId());

        Vacancy vacancy = vacancyMapper.toEntity(request);
        vacancy.setEmployer(employer);
        vacancy.setCreatedAt(LocalDate.now());
        vacancy.setStatus(Status.PENDING);

        vacancyRepository.save(vacancy);
        log.info("Vacancy created: id={}, status=PENDING", vacancy.getId());
    }

    @Transactional
    public VacancyDTO update(UUID id, UpdateVacancyRequest request) {
        Vacancy vacancy = getOwnedVacancy(id);
        log.info("Updating vacancy: id={}, by employer: {}", id, vacancy.getEmployer().getUserId());

        vacancyMapper.updateVacancy(request, vacancy);

        vacancy.setUpdatedAt(LocalDate.now());
        vacancy.setStatus(Status.PENDING);

        vacancyRepository.save(vacancy);

        log.info("Vacancy updated: id={}, status reset to PENDING", id);
        return vacancyMapper.toDTO(vacancy);
    }

    @Transactional
    public void archive(UUID id) {
        Vacancy vacancy = getOwnedVacancy(id);
        log.info("Archiving vacancy: id={}", id);

        if (vacancy.getDeletedAt() == null) {
            vacancy.setDeletedAt(LocalDate.now());
            vacancyRepository.save(vacancy);
            log.info("Vacancy archived successfully: id={}", id);
        }
    }

    private Vacancy getOwnedVacancy(UUID vacancyId) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new NotFoundException("Vacancy not found"));

        EmployerProfile currentEmployer = getCurrentEmployer();

        if (!vacancy.getEmployer().getUserId().equals(currentEmployer.getUserId())) {
            throw new AccessDeniedException("You do not own this vacancy");
        }

        if (vacancy.getDeletedAt() != null) {
            throw new NotFoundException("Vacancy is archived");
        }

        return vacancy;
    }

    private EmployerProfile getCurrentEmployer() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserPrincipal userPrincipal)) {
            throw new BadCredentialsException("Invalid authentication principal");
        }

        return employerProfileRepository.findByUserId(userPrincipal.getId());
    }
}
