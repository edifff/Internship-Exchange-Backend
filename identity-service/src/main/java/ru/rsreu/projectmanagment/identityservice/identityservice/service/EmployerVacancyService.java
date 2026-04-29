package ru.rsreu.projectmanagment.identityservice.identityservice.service;

import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.CreateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.request.UpdateVacancyRequest;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.dto.response.VacancyDTO;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.EmployerProfile;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.UserPrincipal;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.entity.Vacancy;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.enums.Status;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.EmployerProfileRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.data.repository.VacancyRepository;
import ru.rsreu.projectmanagment.identityservice.identityservice.mapper.VacancyMapper;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployerVacancyService {

    private final VacancyRepository vacancyRepository;
    private final VacancyMapper mapper;
    private final EmployerProfileRepository employerProfileRepository;
    private final VacancyMapper vacancyMapper;

    public void create(CreateVacancyRequest createVacancyResponse) {
        EmployerProfile employer = getCurrentEmployer();

        Vacancy vacancy = mapper.toEntity(createVacancyResponse);

        vacancy.setEmployer(employer);
        vacancy.setCreatedAt(LocalDate.now());
        vacancy.setDeletedAt(null);
        vacancy.setUpdatedAt(null);
        vacancy.setStatus(Status.PENDING);

        vacancyRepository.save(vacancy);
    }

    public VacancyDTO update(UUID id, UpdateVacancyRequest request) {
        Vacancy vacancy = getOwnedVacancy(id);

        if(request.getTitle()!=null){
            vacancy.setTitle(request.getTitle());
        }

        if(request.getDescription()!=null){
            vacancy.setDescription(request.getDescription());
        }

        if (request.getCity()!=null){
            vacancy.setCity(request.getCity());
        }

        if (request.getStartedAt()!=null){
            vacancy.setStartDate(request.getStartedAt());
        }

        if(request.getEndedAt()!=null){
            vacancy.setEndDate(request.getEndedAt());
        }

        if(request.getSpecialtys()!=null){
            vacancy.setSpecialties(request.getSpecialtys());
        }

        vacancy.setUpdatedAt(LocalDate.now());
        vacancy.setStatus(Status.PENDING);

        vacancyRepository.save(vacancy);

        return vacancyMapper.toDTO(vacancy);
    }

    public void archive(UUID id) {
        Vacancy vacancy = getOwnedVacancy(id);

        if (vacancy.getDeletedAt() == null) {
            vacancy.setDeletedAt(LocalDate.now());
            vacancyRepository.save(vacancy);
        }
    }

    private Vacancy getOwnedVacancy(UUID vacancyId) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new RuntimeException("Vacancy not found"));

        EmployerProfile currentEmployer = getCurrentEmployer();

        if (!vacancy.getEmployer().getUserId().equals(currentEmployer.getUserId())) {
            throw new AccessDeniedException("You do not own this vacancy");
        }

        if (vacancy.getDeletedAt() != null) {
            throw new RuntimeException("Vacancy is archived");
        }

        return vacancy;
    }

    private EmployerProfile getCurrentEmployer() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserPrincipal userPrincipal)) {
            throw new RuntimeException("Invalid authentication principal");
        }

        return employerProfileRepository.findByUserId(userPrincipal.getId());
    }
}
